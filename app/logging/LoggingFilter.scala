package logging

import akka.stream.Materializer
import com.google.inject.Inject
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class LoggingFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext) extends Filter {

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
