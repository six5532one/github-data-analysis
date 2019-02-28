package emilyc

import GithubQueryBusinessImpl.PaginationParams
import emilyc.models.{ CodesOfConductResponse, CodeOfConduct, OrganizationResponse, OrganizationRepositoryEdge, Edge, Repository }
import emilyc.codec.Serializer

import scala.util.{ Try, Success, Failure }

object GithubQueryBusinessImpl {
  val GithubAPIEndpoint = "https://api.github.com/graphql"
  case class PaginationParams(pageSize: Int, cursor: Option[String])
}

class GithubQueryBusinessImpl(graphQlClient: GraphQlClient = GraphQlClientImpl(GithubQueryBusinessImpl.GithubAPIEndpoint)) extends GithubQueryBusiness {
  def getCodesOfConduct: Try[List[CodeOfConduct]] = {
    val query = "{ codesOfConduct { body } }"
    graphQlClient.execute[CodesOfConductResponse](query)
      .map(_.data.codesOfConduct)
  }

  def getRepositoriesPage(paginationParams: PaginationParams): Try[List[OrganizationRepositoryEdge]] = {
    val after = paginationParams.cursor.map(c => s", after: $c").getOrElse("")
    val query = s"""
      { organization(login: "elastic") {
        repositories(first: ${paginationParams.pageSize}$after) {
          edges {
            cursor
            node {
              name
              isPrivate
              forkCount
              issues {
                totalCount
              }
            }
          }
        }
      }
    }
    """
    println("=========  query ========")
    println(query)
    val res = graphQlClient.execute[OrganizationResponse](query)
    println("==============  result =============")
    println(res)
    res.map(_.data.organization.repositories.edges)
  }

  private def getResults[N, T <: Edge[N]](
    paginationParams: PaginationParams,
    getPage: PaginationParams => Try[List[T]]
  ): Try[List[T]] = getPage(paginationParams) match {
    case failure: Failure[List[T]] => failure
    case Success(Nil) =>
      println("this page is empty")
      Success(Nil)
    case Success(nonemptyList) =>
      println(s"this page has ${nonemptyList.size} results")
      val PaginationParams(pageSize, _) = paginationParams
      val nextPaginationParams = PaginationParams(pageSize, Some(nonemptyList.last.cursor))
      getResults[N, T](nextPaginationParams, getPage).map(nonemptyList ++ _)
  }

  def getRepositories: Try[List[OrganizationRepositoryEdge]] =
    getResults[Repository, OrganizationRepositoryEdge](
      PaginationParams(25, None),
      getRepositoriesPage
    )
}