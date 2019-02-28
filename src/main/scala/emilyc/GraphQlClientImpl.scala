package emilyc

import com.meetup.pro.indexer.{Codec, Serializer}

import org.json4s.JValue
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{compact, render}
import dispatch._

import scala.util.{Try, Success, Failure}
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

object GraphQlClientImpl {
  def apply(graphqlEndpoint: String): GraphQlClientImpl = new GraphQlClientImpl(
    graphqlEndpoint,
    Http.default,
    1.minutes)
}

class GraphQlClientImpl(
    endpoint: String,
    httpExecutor: HttpExecutor,
    timeoutInterval: FiniteDuration) extends GraphQlClient {

  def execute[T: Codec](query: String): Try[T] = {
    val token = sys.env("GITHUB_TOKEN")
    val body = compact(render(("query" -> query)))
    val request = url(endpoint)
      .POST
      .setBody(body.getBytes("UTF-8"))
      .addHeader("Authorization", s"bearer $token")
    Try(Await.result(httpExecutor(request), timeoutInterval))
      .map(response => Serializer.deserialize[T](response.getResponseBody))
      .flatMap {
        _ match {
          case Right(data) => Success(data)
          case Left(errMsg) => Failure(new Throwable(errMsg))
        }
      }
  }
}