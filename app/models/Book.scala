package models

import java.util.UUID

import anorm._
import io.swagger.annotations.{ApiModelProperty, ApiModel}
import play.api.db.DB
import play.api.Play.current

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

@ApiModel
case class Book(
                 @ApiModelProperty(required=true) name: String,
                 @ApiModelProperty(required=true) author: String,
                 @ApiModelProperty(dataType="String", required=false) id: Option[String]
               )

object Book {

  final private val NAME_JSON_PROPERTY = "my_name"
  final private val AUTHOR_JSON_PROPERTY = "my_author"
  final private val ID_JSON_PROPERTY = "id"


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

  def findById(id: String) = {
    books.filter(el => el.id.equals(Option.apply(id))) match {
      case Nil => Option.empty
      case el::Nil => Option(el)
      case el::tail => throw new Exception
    }
  }

  def findBooksForUser(username: String) =  books.filter(el => el.author.equals(username))

  def findById(id: String, username: String) : Option[Book] = {
    books.filter(el => (el.id equals Option.apply(id)) && (el.author equals username)) match {
      case Nil => Option.empty
      case el::Nil => Option(el)
      case el::tail => throw new Exception
    }
  }

  def canUserAccessBook(user: String, book: Book) = book.author == user

  def setId(book: Book) = Book(book.name, book.author, Option(UUID.randomUUID.toString))

  def addBook(book: Book) = {
    books = books :+ setId(book)
    DB.withConnection { implicit c =>
      SQL("insert into book(name, author, id) values ({name}, {author}, {id})")
        .on('name -> books.last.name, 'author -> books.last.author, 'id -> books.last.id).execute();
    }
    books.last
  }
}
