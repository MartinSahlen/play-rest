import javax.inject.Inject

import logging.LoggingFilter
import play.api.http.HttpFilters
import play.filters.cors.CORSFilter
import play.filters.gzip.GzipFilter
import com.kenshoo.play.metrics.MetricsFilter

class Filters @Inject() (corsFilter: CORSFilter, gzipFilter: GzipFilter, metricsFilter: MetricsFilter, loggingFilter: LoggingFilter) extends HttpFilters {
  def filters = Seq(corsFilter, gzipFilter, metricsFilter, loggingFilter)
}
