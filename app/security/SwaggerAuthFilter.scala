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
    val authHeaderOption = Option(headers.get("X-Username"))
    authHeaderOption match {
      case Some(authHeader) =>
        if (!authHeader.isEmpty) {
          val token = authHeader.get(0)
          logger.info(s"Auth header token: $token")
        }
      case _ =>
        logger.info(s"No auth header present")
    }
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
