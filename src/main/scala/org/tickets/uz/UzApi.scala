package org.tickets.uz

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.HostConnectionPool
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{Materializer, OverflowStrategy}
import org.json4s.Reader
import org.tickets.misc.LogSlf4j
import org.tickets.misc.HttpSupport.{Bound, Request, Response}


object UzApi extends LogSlf4j {
  val RootPageHost = "booking.uz.gov.ua"
  val RootPage = s"http://$RootPageHost"

  /**
    * Http Pool for UZ API.
    * @return flow for it
    */
  def httpPoolUz(implicit as: ActorSystem, mt: Materializer): Flow[Request, Response, HostConnectionPool] =
    Http().newHostConnectionPool[Bound]("booking.uz.gov.ua")

  /**
    * Simple publishing actor ref.
    * @return ref
    */
  def publisherRef(implicit as: ActorSystem, mt: Materializer): ActorRef = {
    val publisher = Source.actorRef(100, OverflowStrategy.dropHead)

    httpPoolUz
      .to(Sink.actorSubscriber(UzApiSubscriber.props(mt)))
      .runWith(publisher)
  }

}





case class Route(name: String)