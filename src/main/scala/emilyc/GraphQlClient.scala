package emilyc

import emilyc.codec.Codec

import org.json4s.JValue
import scala.util.Try

trait GraphQlClient {
  def execute[T: Codec](query: String): Try[T]
}