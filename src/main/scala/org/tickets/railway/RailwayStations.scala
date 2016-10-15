package org.tickets.railway

import org.tickets.Station

import scala.concurrent.Future

/**
  * Railway stations.
  */
trait RailwayStations {

  /**
    * Find stations by given name.
    * @param byName like name
    * @return stations
    */
  def findStations(byName: String): Future[List[Station]]
}