package com.github.bsnisar.tickets

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.scaladsl.Flow
import org.json4s._

import scala.util.Try

object Ws  {
  type Task = Any
  type Req = (HttpRequest, Task)
  type Res = (Try[HttpResponse], Task)

  type HttpFlow = Flow[Req, Res, Any]

  type WsFlow = Flow[Req, JValue, Any]

}
