package logging

import play.api.Logger
import play.api.mvc._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class LoggingFilter extends Filter {

  val logger = Logger(this.getClass)

  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {

    val startTime = System.currentTimeMillis

    nextFilter(requestHeader).map { result =>

      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime

      logger.info(s"${requestHeader.method} ${requestHeader.uri}" + s" from IP ${requestHeader.remoteAddress} " +
        s"took ${requestTime}ms and returned ${result.header.status}")

      result.withHeaders("X-Request-Time" -> (requestTime.toString + "ms"))
    }
  }
}
