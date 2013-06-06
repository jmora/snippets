package com.github.jmora.snippets.localstar

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Inbox
import scala.concurrent.duration._

case object Ready
case class Task(ps: PartialSolution)
case class Final(ps: PartialSolution)

class LocalStar(start: PartialSolution) {
  val system = ActorSystem("localstar")
  val inbox = Inbox.create(system)
  val manager = system.actorOf(Props[Manager], "manager")
  for (i <- 1 to 5)
    manager.tell(Ready, system.actorOf(Props[Explorer]))
  manager ! Task(start)
}