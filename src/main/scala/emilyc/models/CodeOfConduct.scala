package emilyc.models

/**
 * abc
 */
sealed case class CodeOfConduct(
  body: String
)

object CodeOfConduct {

  import emilyc.codec.Codec

  import org.json4s._

  implicit val CodeOfConductCodec = new Codec[CodeOfConduct] {
    def encode(a: CodeOfConduct)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, CodeOfConduct] =
      try {
        Right(j.extract[CodeOfConduct])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val CodeOfConductListCodec = new Codec[List[CodeOfConduct]] {
    def encode(a: List[CodeOfConduct])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[CodeOfConduct]] =
      try {
        Right(j.extract[List[CodeOfConduct]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

