import javax.inject.Inject

import play.api.http.HttpFilters
import play.filters.cors.CORSFilter
import play.filters.gzip.GzipFilter
import com.kenshoo.play.metrics.MetricsFilter

class Filters @Inject() (corsFilter: CORSFilter, gzipFilter: GzipFilter, metricsFilter: MetricsFilter) extends HttpFilters {
  def filters = Seq(corsFilter, gzipFilter, metricsFilter)
}