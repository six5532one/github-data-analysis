package emilyc.models

sealed case class RepositoryIssues(
  totalCount: Int)

object RepositoryIssues {

  import com.meetup.pro.indexer.Codec

  import org.json4s._

  implicit val RepositoryIssuesCodec = new Codec[RepositoryIssues] {
    def encode(a: RepositoryIssues)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, RepositoryIssues] =
      try {
        Right(j.extract[RepositoryIssues])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val RepositoryIssuesListCodec = new Codec[List[RepositoryIssues]] {
    def encode(a: List[RepositoryIssues])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[RepositoryIssues]] =
      try {
        Right(j.extract[List[RepositoryIssues]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

