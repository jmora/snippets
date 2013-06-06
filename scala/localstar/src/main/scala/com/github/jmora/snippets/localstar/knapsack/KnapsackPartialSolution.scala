package com.github.jmora.snippets.localstar.knapsack

import com.github.jmora.snippets.localstar.PartialSolution

// Tuples: (weight, value)
case class KnapsackPartialSolution(val pending: List[(Int, Int)], val inside: List[(Int, Int)], val maxWeight: Int) extends PartialSolution {

  val weight = inside.map(_._1).sum
  val realValue = inside.map(_._2).sum
  val valid = maxWeight > weight
  lazy val expansion = pending.map(p => new KnapsackPartialSolution(pending diff List(p), (p::inside).sorted, maxWeight)).filter(_.isValid)
  
  def isValid() = valid
  def isFinal() = expansion.isEmpty
  def expand() = expansion
  def heuristicValue() = (realValue * realValue).toFloat /  weight
}
