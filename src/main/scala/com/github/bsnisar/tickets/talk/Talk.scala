package com.github.bsnisar.tickets.talk

import akka.actor.{Actor, ActorRef, Props}
import com.github.bsnisar.tickets.telegram.ac.TelegramPush
import com.github.bsnisar.tickets.telegram.{TelegramMessages, TgUpdate}
import com.typesafe.scalalogging.LazyLogging


object Talk {
  def props(charID: String,  stationsSearch: ActorRef, telegram: ActorRef): Props =
    Props(classOf[Talk], charID, stationsSearch, telegram)
}

class Talk(val charID: String,
           val stationsSearch: ActorRef,
           val telegram: ActorRef) extends Actor with LazyLogging {

  override def receive: Receive = {
    case update: TgUpdate =>
      update.text match {
        case StationsSearch.StationsSearchCommands(_*) =>
          stationsSearch ! update
        case e =>
          logger.warn(s"unknown text command $e")
      }

    case send: TelegramMessages.SendMsg =>
      telegram ! TelegramPush.PushMessage(charID, send)
  }
}
