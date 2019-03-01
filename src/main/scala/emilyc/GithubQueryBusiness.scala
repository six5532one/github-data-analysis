package emilyc

import emilyc.models.Repository
import scala.util.Try

trait GithubQueryBusiness {
  def repositoriesWithMostForks(organizationName: String, n: Int): Try[List[Repository]]
  def repositoriesWithMostIssues(organizationName: String, n: Int): Try[List[Repository]]
}