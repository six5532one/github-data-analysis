package emilyc.models

sealed case class OrganizationResponse(
  data: OrganizationData
)

object OrganizationResponse {

  import emilyc.codec.Codec

  import org.json4s._

  implicit val OrganizationResponseCodec = new Codec[OrganizationResponse] {
    def encode(a: OrganizationResponse)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, OrganizationResponse] =
      try {
        Right(j.extract[OrganizationResponse])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val OrganizationResponseListCodec = new Codec[List[OrganizationResponse]] {
    def encode(a: List[OrganizationResponse])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[OrganizationResponse]] =
      try {
        Right(j.extract[List[OrganizationResponse]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

