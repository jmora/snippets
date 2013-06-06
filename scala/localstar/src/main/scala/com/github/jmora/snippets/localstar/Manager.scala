package com.github.jmora.snippets.localstar

import akka.actor.ActorRef
import akka.actor.Actor
import scala.collection.mutable.Queue
import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.Set

class Manager extends Actor {

  val actors: Queue[ActorRef] = new Queue[ActorRef]()
  val tasks: PriorityQueue[PartialSolution] = new PriorityQueue[PartialSolution]()
  val processed: Set[String] = Set()

  def isNew(ps: PartialSolution) = {
    val prevsize = processed.size
    processed.add(ps.toString)
    prevsize < processed.size
  }

  def receive = {
    case Ready => if (tasks.isEmpty) actors += sender else sender ! Task(tasks.dequeue)
    case Task(ps) => if (isNew(ps)) (if (actors.isEmpty) tasks += ps else actors.dequeue ! Task(ps))
    case Final(ps) => println(s"final\n\treal:\t${ps.realValue}\n\theuristic:\t${ps.heuristicValue}\n\tsolution:\t$ps")
  }
}