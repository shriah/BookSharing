package controllers

import scala.concurrent.Future

import javax.inject.Inject
import models.BooksRow

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.JsPath
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import org.joda.time.DateTime
import java.sql.Date
import service.ComponentRegistry


class Application extends Controller {
  val componentRegistry = new ComponentRegistry();


  def listBooks = Action.async { implicit request =>
    val booksFuture = componentRegistry.booksService.all
    booksFuture.map( books => Ok(Json.toJson(books)) )
    
  }
  
  def createBook = Action.async { implicit request =>
    val body: AnyContent = request.body
    val jsonBody: Option[JsValue] = body.asJson
    jsonBody.map { json =>
      json.validate[BooksRow].map { book =>
          componentRegistry.booksService.create(book).map { id =>
           Ok("Created Book")
         }
      }.getOrElse{
       Future.successful(BadRequest("Failed to parse book"))
      }
        
    }.getOrElse{
      Future.successful(BadRequest("Expecting application/json request body"))
    }
    
  }
  def modifyBook(id: Int, name:Option[String], edition: Option[String] , binding: Option[String], published: Option[String], price: Option[Double], media: Option[String]) = Action.async{ implicit response =>
    val publishedDate = published.map( publishedValue => new Date(DateTime.parse(publishedValue).getMillis))
     componentRegistry.booksService.partialUpdate(id, name, edition, binding, publishedDate, price, media).map { Int =>
      Ok("Book Updated")
    }
  
  }

  def deleteBook(id: Int) = Action.async { implicit request =>
     componentRegistry.booksService.delete(id).map(num => Ok("Deleted Book"))
  }
  def books(id: Int) = Action.async { implicit request => 
     componentRegistry.booksService.findById(id).map { bookOption => 
      bookOption
        .map(book => Ok(Json.toJson(book)))
        .getOrElse(NotFound)
    }
  }

}
