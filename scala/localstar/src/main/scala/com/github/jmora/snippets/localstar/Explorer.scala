package com.github.jmora.snippets.localstar

import akka.actor.Actor

class Explorer extends Actor {
  def receive = {
    case Task(ps: PartialSolution) => {
      if (ps.isFinal)
        sender ! Final(ps)
      else
        for (t <- ps.expand if t.isValid)
          sender ! Task(t)
      sender ! Ready
    }
  }
}