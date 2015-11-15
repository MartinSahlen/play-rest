package auth

import play.api.mvc._

import scala.concurrent.Future

object SecuredAction extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    print(request.headers);
    block(request)
  }
}
