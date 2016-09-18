package org.tickets

import akka.actor.ActorSystem
import com.google.inject.Guice
import org.tickets.api.ApiModule
import org.tickets.misc.Log


object Main extends App with Log {
  val init = System.currentTimeMillis()

  val system = ActorSystem("tickets-bot")

  val injector = Guice.createInjector(
    new ApiModule(system)
  )

  log.info("Started in {}", System.currentTimeMillis() - init)
}