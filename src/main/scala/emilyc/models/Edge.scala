package emilyc.models

trait Edge[T] {
  val cursor: String
  val node: T
}