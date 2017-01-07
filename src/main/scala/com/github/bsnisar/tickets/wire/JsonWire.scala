package com.github.bsnisar.tickets.wire

import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import com.github.bsnisar.tickets.Ws.{Req, Res}
import com.github.bsnisar.tickets.misc.Json
import org.json4s.JValue

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Wire for transforming response as JSON.
  *
  * @param origin original wire
  */
class JsonWire(private val origin: Wire[Req, Res])(private implicit val mt: Materializer)
  extends Wire[Req, JValue]  with Json {


  private lazy val _flow =
    Flow[Req].via(origin.flow).via(asJsonFlow)

  override def flow: Flow[Req, JValue, _] = _flow

  /**
    * Parse response as json.
    * @return new flow.
    */
  private def asJsonFlow(implicit mt: Materializer): Flow[Res, JValue, Any]  = Flow[Res].mapAsync(1) {
    case (Success(httpResp), _) if httpResp.status.isSuccess() =>
      Unmarshal(httpResp.entity).to[JValue]
    case (Success(httpResp), _) if !httpResp.status.isSuccess() =>
      throw new IllegalStateException(s"http status ${httpResp.status}")
    case (Failure(err), _) =>
      throw err
  }
}
