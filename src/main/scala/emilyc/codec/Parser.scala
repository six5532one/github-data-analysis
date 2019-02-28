package emilyc.codec

import org.json4s._
import org.json4s.native.JsonMethods
import org.json4s.native.JsonMethods.{compact, render}

object Parser {
  def parse(s: String): Either[String, JValue] =
    try {
      org.json4s.native.JsonMethods.parse(s) match {
        case JNothing => Left(s"Unexpected input: $s")
        case jvalue => Right(jvalue)
      }
    } catch {
      case scala.util.control.NonFatal(e) => Left(e.getMessage)
    }

  def deparse[A](j: JValue): String =
    j match {
      case JNothing => ""
      case _ => compact(render(j))
    }
}