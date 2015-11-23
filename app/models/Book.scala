package models

import java.util.UUID

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


object Book {

  final private val NAME_JSON_PROPERTY = "my_name"
  final private val AUTHOR_JSON_PROPERTY = "my_author"
  final private val ID_JSON_PROPERTY = "id"

  case class Book(name: String, author: String, id: Option[String])

  implicit val bookWrites: Writes[Book] = (
      (JsPath \ NAME_JSON_PROPERTY).write[String] and
      (JsPath \ AUTHOR_JSON_PROPERTY).write[String] and
      (JsPath \ ID_JSON_PROPERTY).writeNullable[String]
    )(unlift(Book.unapply))

  implicit val bookReads: Reads[Book] = (
      (JsPath \ NAME_JSON_PROPERTY).read[String](minLength[String](2)) and
      (JsPath \ AUTHOR_JSON_PROPERTY).read[String](minLength[String](2)) and
      (JsPath \ ID_JSON_PROPERTY).readNullable[String]
    )(Book.apply _)

  var books = Seq[Book]()

  def findById(id: String) : Option[Book] = {
    books.filter(el => el.id.equals(Option.apply(id))) match {
      case Nil => Option.empty
      case el::Nil => Option(el)
      case el::tail => throw new Exception
    }
  }

  def findBooksForUser(username: String) =  books.filter(el => el.author.equals(username))

  def findById(id: String, username: String) : Option[Book] = {
    books.filter(el => el.id.equals(Option.apply(id)) && el.author == username) match {
      case Nil => Option.empty
      case el::Nil => Option(el)
      case el::tail => throw new Exception
    }
  }

  def canUserAccessBook(user: String, book: Book) = book.author == user

  def setId(book: Book) : Book = Book(book.name, book.author, Option(UUID.randomUUID.toString))

  def addBook(book: Book) = books = books :+ setId(book)
}
