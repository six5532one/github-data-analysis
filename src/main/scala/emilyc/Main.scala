package emilyc

import scala.util.{Try, Success, Failure}

case class ScriptOptions(
                          organizationName: Option[String] = None,
                          n: Option[Int] = None,
                          query: String = ""
                        )

trait ValidatedScriptOptions
case class WithMostForksOptions(organizationName: String, n: Int) extends ValidatedScriptOptions
case class WithMostIssuesOptions(organizationName: String, n: Int) extends ValidatedScriptOptions

object Main extends App {
  private val Flag = """(\w+)=(.+)""".r
  private val FlagName = """-(\w+)""".r
  val usage: String = """
      Usage: -repos_with_most_forks organization=<orgName> n=<n>
      Usage: -repos_with_most_issues organization=<orgName> n=<n>
    """
  val options: Either[String, ScriptOptions] = args.foldLeft[Either[String, ScriptOptions]](Right(ScriptOptions())) {
    case (Right(opts), Flag("organization", orgName)) =>
      Right(opts.copy(organizationName=Some(orgName)))
    case (Right(opts), Flag("n", n)) => Try(n.toInt).toOption match {
      case nonEmptyInt@Some(i) if i > 0 => Right(opts.copy(n=nonEmptyInt))
      case _ => Left(s"$n is not a valid value for n")
    }
    case (Right(opts), Flag(flag, _)) =>
      Left(s"$flag is not a supported flag.\n$usage\n")
    case (Right(opts), FlagName("repos_with_most_forks")) =>
      Right(opts.copy(query="repos_with_most_forks"))
    case (Right(opts), FlagName("repos_with_most_issues")) =>
      Right(opts.copy(query="repos_with_most_issues"))
    case (Right(opts), FlagName("help")) =>
      Left(usage)
    case (Right(opts), FlagName(flagName)) =>
      Left(s"$flagName is not a supported flag name.\n$usage\n")
    case (Left(err), _) => Left(err)
    case _ =>
      Left(usage)
  }

  def validateOptions(options: ScriptOptions): Either[String, ValidatedScriptOptions] = options match {
    case ScriptOptions(Some(orgName), Some(n), "repos_with_most_forks") =>
      Right(WithMostForksOptions(orgName, n))
    case ScriptOptions(Some(orgName), Some(n), "repos_with_most_issues") =>
      Right(WithMostIssuesOptions(orgName, n))
    case ScriptOptions(Some(orgName), Some(n), otherFlagname) =>
      Left(s"$otherFlagname is not a valid query")
    case ScriptOptions(None, _, _) =>
      Left("must specify the organization name")
    case ScriptOptions(_, None, _) =>
      Left("must specify the number of repositories to return")
  }

  options.right.flatMap(validateOptions) match {
    case Left(err) =>
      println(err)
    case Right(WithMostForksOptions(orgName, n)) =>
      val business = new GithubQueryBusinessImpl()
      business.repositoriesWithMostForks(orgName, n) match {
        case Success(repos) => repos.foreach(repo => println(s"Repository: ${repo.name}\nNumber of forks: ${repo.forkCount}\n\n"))
        case Failure(throwable) => println(throwable)
      }
    case Right(WithMostIssuesOptions(orgName, n)) =>
      val business = new GithubQueryBusinessImpl()
      business.repositoriesWithMostIssues(orgName, n) match {
        case Success(repos) => repos.foreach(repo => println(s"Repository: ${repo.name}\nNumber of issues: ${repo.issues.totalCount}\n\n"))
        case Failure(throwable) => println(throwable)
      }
    case _ =>
      println("Unknown error. Exiting")
  }
}
