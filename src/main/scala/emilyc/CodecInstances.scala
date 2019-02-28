package emilyc

import org.json4s._
import org.json4s.native.Serialization

trait CodecInstances {
  implicit val UnitCodec =
    new Codec[Unit] {
      def encode(a: Unit)(implicit formats: Formats): JValue = JNothing
      def decode(j: JValue)(implicit formats: Formats): Either[String, Unit] = Right(Unit)
    }
}