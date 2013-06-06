package com.github.jmora.snippets.localstar

trait PartialSolution extends Serializable with Ordered[PartialSolution] {
  def isValid(): Boolean
  def isFinal(): Boolean
  def expand(): Seq[PartialSolution]
  def heuristicValue(): Double
  def realValue(): Int
  def compare(ps: PartialSolution) = this.heuristicValue compareTo ps.heuristicValue 
}