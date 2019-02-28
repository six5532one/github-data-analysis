package emilyc.models

/**
 * aaa
 */
sealed case class CodeOfConductResponse(
  data: CodesOfConductData)

object CodeOfConductResponse {

  import com.meetup.pro.indexer.Codec

  import org.json4s._

  implicit val CodeOfConductResponseCodec = new Codec[CodeOfConductResponse] {
    def encode(a: CodeOfConductResponse)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, CodeOfConductResponse] =
      try {
        Right(j.extract[CodeOfConductResponse])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val CodeOfConductResponseListCodec = new Codec[List[CodeOfConductResponse]] {
    def encode(a: List[CodeOfConductResponse])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[CodeOfConductResponse]] =
      try {
        Right(j.extract[List[CodeOfConductResponse]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

