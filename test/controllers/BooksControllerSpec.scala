package controllers

import models.Book
import models.Book._
import play.api.libs.json.JsValue

import scala.concurrent.Future
import org.scalatestplus.play._

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class BooksControllerSpec extends PlaySpec with Results {

  class TestBooksController() extends Controller with BooksController

  "BooksController listBooks#" should {
    "Authenticated request should return valid JSON" in {
      val controller = new TestBooksController()
      val request = FakeRequest().withHeaders(("X-Username", "Martin"))
      val result: Future[Result] = controller.listBooks().apply(request)
      val bodyJson: JsValue = contentAsJson(result)
      bodyJson.validate[Seq[Book]].isSuccess mustEqual true
      status(result) mustEqual  OK
    }
  }
}
