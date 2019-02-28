package emilyc.models

sealed case class Repository(
  name: String,
  isPrivate: Boolean,
  forkCount: Int,
  issues: RepositoryIssues)

object Repository {

  import emilyc.codec.Codec

  import org.json4s._

  implicit val RepositoryCodec = new Codec[Repository] {
    def encode(a: Repository)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, Repository] =
      try {
        Right(j.extract[Repository])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val RepositoryListCodec = new Codec[List[Repository]] {
    def encode(a: List[Repository])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[Repository]] =
      try {
        Right(j.extract[List[Repository]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

