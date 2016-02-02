package controllers

import io.swagger.annotations._
import models.Book
import models.Book._
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._

@Api(value = "/books")
trait BooksController {
  this: Controller =>

  @ApiOperation(value = "List Books",
    nickname = "listBooks",
    notes = "Returns all books you have access to",
    response = classOf[Book],
    responseContainer = "List",
    httpMethod = "GET")
  @ApiResponses(Array(
    new ApiResponse(code = 403, message = "Not authenticated")))
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      value = "Token for logged in user.",
      name = "Authorization",
      dataType = "string",
      paramType = "header")))
  def listBooks = (UserAction andThen AuthenticationCheckAction) { request =>
    Ok(Json.toJson(findBooksForUser(request.username.get)))
  }

  @ApiOperation(value = "Create Book",
    nickname = "createBook",
    notes = "Creates and returns book",
    response = classOf[Book],
    code = 201,
    httpMethod = "POST")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      name = "book",
      value = "Book to add",
      required = true,
      dataType = "models.Book",
      paramType = "body")))
  @ApiResponses(Array(
    new ApiResponse(code = 201, message = "Created"),
    new ApiResponse(code = 400, message = "Bad request"),
    new ApiResponse(code = 403, message = "Not authenticated")))
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

  @ApiOperation(value = "Get Book",
    nickname = "getBook",
    notes = "Returns a book if you have access to it",
    response = classOf[Book],
    httpMethod = "GET")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok"),
    new ApiResponse(code = 403, message = "Not authenticated")))
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

object BooksController extends Controller with BooksController

class UserRequest[A](val username: Option[String], request: Request[A]) extends WrappedRequest[A](request)

object AuthenticationCheckAction extends ActionFilter[UserRequest] {
  private val logger = Logger(this.getClass)
  def filter[A](request: UserRequest[A]) = Future.successful {
    request.username match {
      case Some("Martin") => None
      case _ =>
        logger.info("Not authenticated, aborting request")
        Option(Forbidden(Json.obj("message" -> "forbidden")))
    }
  }
}

object UserAction extends ActionBuilder[UserRequest] with ActionTransformer[Request, UserRequest] {
  def transform[A](request: Request[A]) = Future.successful {
    new UserRequest(request.headers.get("X-Username"), request)
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
