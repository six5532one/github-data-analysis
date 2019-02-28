package emilyc.models

sealed case class Organization(
  repositories: OrganizationRepositoriesConnection)

object Organization {

  import emilyc.codec.Codec

  import org.json4s._

  implicit val OrganizationCodec = new Codec[Organization] {
    def encode(a: Organization)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, Organization] =
      try {
        Right(j.extract[Organization])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val OrganizationListCodec = new Codec[List[Organization]] {
    def encode(a: List[Organization])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[Organization]] =
      try {
        Right(j.extract[List[Organization]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

