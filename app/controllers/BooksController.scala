package controllers

import models.Book._
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._

object BooksController extends Controller {

  def listBooks = (UserAction andThen AuthenticationCheckAction) { request =>
    Ok(Json.toJson(findBooksForUser(request.username.get)))
  }

  def saveBook = (UserAction andThen AuthenticationCheckAction)(BodyParsers.parse.json) { request =>
    request.body.validate[Book].fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      book => {
        Created(Json.toJson(addBook(book)))
      }
    )
  }

  def findBook(bookId: String) = (UserAction andThen SpecificBookAction(bookId) andThen BookPermissionCheckAction) { request =>
    Ok(Json.toJson(request.book))
  }

  def SpecificBookAction(itemId: String) = new ActionRefiner[UserRequest, BookRequest] {
    def refine[A](userRequest: UserRequest[A]) = Future.successful {
      findById(itemId)
        .map(new BookRequest(_, userRequest))
        .toRight(NotFound(Json.obj("status" -> "Not found")))
    }
  }
}

class UserRequest[A](val username: Option[String], request: Request[A]) extends WrappedRequest[A](request)

object AuthenticationCheckAction extends ActionFilter[UserRequest] {
  def filter[A](request: UserRequest[A]) = Future.successful {
    request.username match {
      case Some("martin") => None
      case _ => Option(Forbidden(Json.obj("message" -> "forbidden")))
    }
  }
}

object UserAction extends ActionBuilder[UserRequest] with ActionTransformer[Request, UserRequest] {
  def transform[A](request: Request[A]) = Future.successful {
    new UserRequest(Option("martin"), request)
  }
}

class BookRequest[A](val book: Book, request: UserRequest[A]) extends WrappedRequest[A](request) {
  def username = request.username
}

object BookPermissionCheckAction extends ActionFilter[BookRequest] {
  def filter[A](request: BookRequest[A]) = Future.successful {
    request.username match {
      case Some(request.book.author) => None
      case _ => Option(Forbidden(Json.obj("message" -> "forbidden, you did not write the book")))
    }
  }
}


