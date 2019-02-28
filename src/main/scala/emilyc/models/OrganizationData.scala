package emilyc.models

sealed case class OrganizationData(
  organization: Organization)

object OrganizationData {

  import emilyc.codec.Codec

  import org.json4s._

  implicit val OrganizationDataCodec = new Codec[OrganizationData] {
    def encode(a: OrganizationData)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, OrganizationData] =
      try {
        Right(j.extract[OrganizationData])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val OrganizationDataListCodec = new Codec[List[OrganizationData]] {
    def encode(a: List[OrganizationData])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[OrganizationData]] =
      try {
        Right(j.extract[List[OrganizationData]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

