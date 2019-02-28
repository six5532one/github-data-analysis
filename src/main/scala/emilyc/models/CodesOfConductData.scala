package emilyc.models

/**
 * bcd
 */
sealed case class CodesOfConductData(
  codesOfConduct: List[CodeOfConduct]
)

object CodesOfConductData {

  import emilyc.codec.Codec

  import org.json4s._

  implicit val CodesOfConductDataCodec = new Codec[CodesOfConductData] {
    def encode(a: CodesOfConductData)(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, CodesOfConductData] =
      try {
        Right(j.extract[CodesOfConductData])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

  implicit val CodesOfConductDataListCodec = new Codec[List[CodesOfConductData]] {
    def encode(a: List[CodesOfConductData])(implicit formats: Formats): JValue = Extraction.decompose(a)

    def decode(j: JValue)(implicit formats: Formats): Either[String, List[CodesOfConductData]] =
      try {
        Right(j.extract[List[CodesOfConductData]])
      } catch {
        case e: MappingException => Left(e.getCause.getMessage)
        case scala.util.control.NonFatal(e) => Left(e.getMessage)
      }
  }

}

