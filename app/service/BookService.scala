package service

import scala.concurrent.Future
import models.BooksRow
import repository.BookRepositoryComponent

trait BooksServiceModule {
  this: BookRepositoryComponent =>
  val booksService: BookService
  class DefaultBookService extends BookService {
    def all: Future[List[BooksRow]] = bookRepository.all
    def findById(id: Int): Future[Option[BooksRow]] = bookRepository.findById(id)
    def create(book: models.BooksRow): Future[Int] = bookRepository.create(book)
    def partialUpdate(id: Int, name:Option[String], edition: Option[String] , binding: Option[String], published: Option[java.sql.Date], price: Option[Double], media: Option[String]): Future[Int] = bookRepository.partialUpdate(id, name, edition, binding, published, price, media)
    def delete(id: Int): Future[Int] = bookRepository.delete(id)
  }
}

trait BookService {
  def all: Future[List[BooksRow]]
  def findById(id: Int): Future[Option[BooksRow]]
  def create(book: models.BooksRow): Future[Int]
  def partialUpdate(id: Int, name:Option[String], edition: Option[String] , binding: Option[String], published: Option[java.sql.Date], price: Option[Double], media: Option[String]): Future[Int]
  def delete(id: Int): Future[Int]
}