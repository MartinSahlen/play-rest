package models

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


object Book {

  final private val NAME_JSON_PROPERTY = "my_name"
  final private val AUTHOR_JSON_PROPERTY = "my_author"

  case class Book(name: String, author: String)

  implicit val bookWrites: Writes[Book] = (
      (JsPath \ NAME_JSON_PROPERTY).write[String] and
      (JsPath \ AUTHOR_JSON_PROPERTY).write[String]
    )(unlift(Book.unapply))

  implicit val bookReads: Reads[Book] = (
      (JsPath \ NAME_JSON_PROPERTY).read[String](minLength[String](2)) and
      (JsPath \ AUTHOR_JSON_PROPERTY).read[String](minLength[String](2))
    )(Book.apply _)

  var books = List[Book]()

  def addBook(b: Book) = books =  books :+ b
}
