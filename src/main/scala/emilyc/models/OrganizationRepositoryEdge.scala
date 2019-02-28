package emilyc.models

sealed case class OrganizationRepositoryEdge(
  cursor: String,
  node: Repository) extends Edge[Repository]

object OrganizationRepositoryEdge {

  import com.meetup.pro.indexer.Codec

  import org.json4s._

  implicit val OrganizationRepositoryEdgeCodec = new Codec[OrganizationRepositoryEdge] {
    def encode(a: OrganizationRepositoryEdge)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, OrganizationRepositoryEdge] =
      try {
        Right(j.extract[OrganizationRepositoryEdge])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val OrganizationRepositoryEdgeListCodec = new Codec[List[OrganizationRepositoryEdge]] {
    def encode(a: List[OrganizationRepositoryEdge])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[OrganizationRepositoryEdge]] =
      try {
        Right(j.extract[List[OrganizationRepositoryEdge]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

