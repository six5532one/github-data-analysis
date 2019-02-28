package emilyc

import Codec._
import Parser._

object Serializer {
  def serialize[A: Codec](a: A): String = deparse(encode(a))

  def deserialize[A: Codec](s: String): Either[String, A] =
    parse(s).right.flatMap(decode[A])
}
