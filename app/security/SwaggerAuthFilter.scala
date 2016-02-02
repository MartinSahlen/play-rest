package security

import io.swagger.model._
import io.swagger.core.filter.AbstractSpecFilter
import io.swagger.models.Operation
import io.swagger.models.parameters.Parameter
import play.api.Logger

class SwaggerAuthFilter extends AbstractSpecFilter {
  private val logger = Logger(this.getClass)
  override def isOperationAllowed(operation: Operation, api: ApiDescription, params: java.util.Map[String, java.util.List[String]], cookies: java.util.Map[String, String], headers: java.util.Map[String, java.util.List[String]]) = {
    logger.info(operation.getDescription)
    true
  }

  override def isParamAllowed(parameter: Parameter, operation: Operation, api: ApiDescription, params: java.util.Map[String, java.util.List[String]], cookies: java.util.Map[String, String], headers: java.util.Map[String, java.util.List[String]]): Boolean = {
    logger.info(operation.getDescription)
    true
  }
}
