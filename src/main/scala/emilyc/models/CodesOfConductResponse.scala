package emilyc.models

/**
 * aaa
 */
sealed case class CodesOfConductResponse(
  data: CodesOfConductData)

object CodesOfConductResponse {

  import com.meetup.pro.indexer.Codec

  import org.json4s._

  implicit val CodesOfConductResponseCodec = new Codec[CodesOfConductResponse] {
    def encode(a: CodesOfConductResponse)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, CodesOfConductResponse] =
      try {
        Right(j.extract[CodesOfConductResponse])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val CodesOfConductResponseListCodec = new Codec[List[CodesOfConductResponse]] {
    def encode(a: List[CodesOfConductResponse])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[CodesOfConductResponse]] =
      try {
        Right(j.extract[List[CodesOfConductResponse]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

