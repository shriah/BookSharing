package repository

import db.DBConnection
import scala.concurrent.Future
import models.BooksRow
import models.Tables
import db.DBConnectionComponent

trait BookRepositoryComponent {
  this: DBConnectionComponent with Tables =>
    
  val bookRepository: BookRepository
  
  class DefaultBookRepository extends BookRepository{
    import dbConnection.dc.driver.api._ 
    val db = dbConnection.db
    def all: Future[List[BooksRow]] = db.run(Books.to[List].result)
    def create(book: models.BooksRow): Future[Int] = {
      db.run(Books returning Books.map(_.id) += book)
    }
    def findById(id: Int): Future[Option[BooksRow]] = {
      val query = Books.filter(_.id === id).result.headOption
      db.run(query)
    }
      
    def delete(id: Int): Future[Int] = {
      val query =  Books.filter(_.id === id)
      db.run(query.delete)
    }
    
    def partialUpdate(id: Int, name:Option[String], edition: Option[String] , binding: Option[String], published: Option[java.sql.Date], price: Option[Double], media: Option[String]): Future[Int] = {
      import scala.concurrent.ExecutionContext.Implicits.global
  
      val query = Books.filter(_.id === id)
  
      val update = query.result.head.flatMap {book =>
        query.update(book.patch(name, edition, binding, published, price, media))
      }
  
      db.run(update)
    }
  }
  
}

trait BookRepository {
  def all: Future[List[BooksRow]]
  def findById(id: Int): Future[Option[BooksRow]]
  def create(book: models.BooksRow): Future[Int]
  def partialUpdate(id: Int, name:Option[String], edition: Option[String] , binding: Option[String], published: Option[java.sql.Date], price: Option[Double], media: Option[String]): Future[Int]
  def delete(id: Int): Future[Int]
}