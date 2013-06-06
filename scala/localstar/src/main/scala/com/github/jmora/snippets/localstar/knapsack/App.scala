package com.github.jmora.snippets.localstar.knapsack

import scala.util.Random
import scala.collection.mutable.ListBuffer
import com.github.jmora.snippets.localstar.LocalStar

/**
 * @author jmora
 */
object App {

  def main(args: Array[String]) {
    val n = 200;
    val rg = new Random()
    val pending = new ListBuffer[(Int,Int)]()
    for (i <- 1 to (rg nextInt n))
      pending += ((rg.nextInt(n) + 1, (rg nextInt n) + 1))
    val ls = new LocalStar(new KnapsackPartialSolution(pending.toList.sorted, List(), rg nextInt n))
  }

}
