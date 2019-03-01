package emilyc

import GithubQueryBusinessImpl.PaginationParams
import emilyc.models.{ OrganizationResponse, OrganizationRepositoryEdge, Edge, Repository }

import scala.util.{ Try, Success, Failure }

object GithubQueryBusinessImpl {
  val GithubAPIEndpoint = "https://api.github.com/graphql"
  case class PaginationParams(pageSize: Int, cursor: Option[String])
}

class GithubQueryBusinessImpl(graphQlClient: GraphQlClient = GraphQlClientImpl(GithubQueryBusinessImpl.GithubAPIEndpoint)) extends GithubQueryBusiness {

  private def getRepositoriesPage(organizationName: String)(paginationParams: PaginationParams): Try[List[OrganizationRepositoryEdge]] = {
    val after = paginationParams.cursor.map(c => s", after: $c").getOrElse("")
    val query = s"""
      { organization(login: "$organizationName") {
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
    val res = graphQlClient.execute[OrganizationResponse](query)
    res.map(_.data.organization.repositories.edges)
  }

  private def getResults[N, T <: Edge[N]](
    paginationParams: PaginationParams,
    getPage: PaginationParams => Try[List[T]]
  ): Try[List[T]] = getPage(paginationParams) match {
    case failure: Failure[List[T]] => failure
    case Success(Nil) => Success(Nil)
    case Success(nonemptyList) =>
      val PaginationParams(pageSize, _) = paginationParams
      val nextPaginationParams = PaginationParams(pageSize, Some(nonemptyList.last.cursor))
      getResults[N, T](nextPaginationParams, getPage).map(nonemptyList ++ _)
  }

  private def getRepositories(organizationName: String): Try[List[OrganizationRepositoryEdge]] = {
    val pageSize = 25
    getResults[Repository, OrganizationRepositoryEdge](
      PaginationParams(25, None),
      getRepositoriesPage(organizationName)
    )
  }

  def repositoriesWithMostForks(organizationName: String, n: Int): Try[List[Repository]] =
    getRepositories(organizationName).map { edges =>
        edges.map(_.node).sortWith(_.forkCount > _.forkCount).take(n)
    }

  def repositoriesWithMostIssues(organizationName: String, n: Int): Try[List[Repository]] =
    getRepositories(organizationName).map { edges =>
      edges.map(_.node).sortWith(_.issues.totalCount > _.issues.totalCount).take(n)
    }
}