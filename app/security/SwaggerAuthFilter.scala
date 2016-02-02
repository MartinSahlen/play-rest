package security

import io.swagger.model._
import io.swagger.core.filter.AbstractSpecFilter
import io.swagger.models.Operation
import io.swagger.models.parameters.Parameter
import play.api.Logger
import java.util.{Map => JMap, List => JList}

class SwaggerAuthFilter extends AbstractSpecFilter {
  private val logger = Logger(this.getClass)
  override def isOperationAllowed(operation: Operation,
                                  api: ApiDescription,
                                  params: JMap[String, JList[String]],
                                  cookies: JMap[String, String],
                                  headers: JMap[String, JList[String]]): Boolean = {
    val authHeader = headers.get("X-Username")
    if (authHeader.size > 0) {
      println(authHeader.get(0))
    }
    logger.info(s"Auth header: $authHeader")
    true
  }

  override def isParamAllowed(parameter: Parameter,
                              operation: Operation,
                              api: ApiDescription,
                              params: JMap[String, JList[String]],
                              cookies: JMap[String, String],
                              headers: JMap[String, JList[String]]): Boolean = {
    true
  }
}
