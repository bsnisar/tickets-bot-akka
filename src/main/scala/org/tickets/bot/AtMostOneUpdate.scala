package org.tickets.bot

import akka.actor.{Actor, ActorRef, Props}
import org.tickets.misc.LogSlf4j
import org.tickets.telegram.Update

object AtMostOneUpdate {
  def props(delegat: Props): Props
  = Props(classOf[AtMostOneUpdate], delegat)
}


class AtMostOneUpdate(props: Props) extends Actor with LogSlf4j {
  private var lastUpdateSeqNum: Int = Int.MinValue
  private val talk: ActorRef = context.actorOf(props)

  override def receive: Receive = {
    case update: Update if update.id > lastUpdateSeqNum =>
      lastUpdateSeqNum = update.id
      talk ! Bot.Cmd(update.text, update)
  }
}