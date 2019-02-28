package emilyc

import emilyc.models.{CodeOfConduct, OrganizationRepositoryEdge}
import scala.util.Try

trait GithubQueryBusiness {
  def getCodesOfConduct: Try[List[CodeOfConduct]]
  def getRepositories: Try[List[OrganizationRepositoryEdge]]
}