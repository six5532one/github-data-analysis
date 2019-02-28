package emilyc.models

sealed case class OrganizationRepositoriesConnection(
  edges: List[OrganizationRepositoryEdge])

object OrganizationRepositoriesConnection {

  import com.meetup.pro.indexer.Codec

  import org.json4s._

  implicit val OrganizationRepositoriesConnectionCodec = new Codec[OrganizationRepositoriesConnection] {
    def encode(a: OrganizationRepositoriesConnection)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, OrganizationRepositoriesConnection] =
      try {
        Right(j.extract[OrganizationRepositoriesConnection])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val OrganizationRepositoriesConnectionListCodec = new Codec[List[OrganizationRepositoriesConnection]] {
    def encode(a: List[OrganizationRepositoriesConnection])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[OrganizationRepositoriesConnection]] =
      try {
        Right(j.extract[List[OrganizationRepositoriesConnection]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

