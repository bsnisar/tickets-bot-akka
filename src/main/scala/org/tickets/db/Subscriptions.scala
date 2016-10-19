package org.tickets.db

import org.tickets.model.TicketsCriteria

import scala.concurrent.Future

trait Subscriptions {

  def subscribe(user: String, criteria: TicketsCriteria): Future[TicketsCriteria]
}