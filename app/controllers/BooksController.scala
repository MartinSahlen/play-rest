package controllers

import models.Book._
import auth.SecuredAction
import play.api.libs.json._
import play.api.mvc._


object BooksController extends Controller {

  def listBooks = SecuredAction {
      Ok(Json.toJson(books))
  }

  def saveBook = SecuredAction(BodyParsers.parse.json) { request =>
    val b = request.body.validate[Book]
    b match {
      case JsSuccess(book, _) =>
        addBook(book)
        Ok(Json.obj("status" -> "OK"))
      case errors@JsError(_) =>
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
    }
  }
}
