package auth

import play.api.mvc._
import play.api.mvc.Results.Forbidden
import scala.concurrent.Future


object PermissionCheckAction extends ActionFilter[Request] {
  def filter[A](input: Request[A]) = Future.successful {
    if (true)
      Some(Forbidden)
    else
      None
  }
}