package emilyc.codec

import org.json4s.JValue
import org.json4s.Formats

/**
 * A `Codec[A]` instance provides bidirectional JSON serialization support.
 * Instances of `A` can be encoded to and decoded from a JSON structure
 * represented by the `JValue` type.
 */
trait Codec[A] {
  /**
   * Encode an instance of `A` as an instance of `JValue`.
   */
  def encode(a: A)(implicit formats: Formats): JValue

  /**
   * Decode an instance of `JValue` as an instance of `A`.
   */
  def decode(j: JValue)(implicit formats: Formats): Either[String, A]
}

object Codec extends CodecInstances {

  import emilyc.models._
  import com.meetup.json4s.JavaTimeSerializers
  import org.json4s.native.Serialization

  implicit val formats = Serialization.formats(org.json4s.NoTypeHints) ++ JavaTimeSerializers.defaults

  def encode[A](a: A)(implicit c: Codec[A]): JValue = c.encode(a)

  def decode[A](j: JValue)(implicit c: Codec[A]): Either[String, A] =
    c.decode(j)
}
