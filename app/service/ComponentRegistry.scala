package service

import db.DBConnectionComponent
import models.Tables
import repository.BookRepositoryComponent

class ComponentRegistry extends BooksServiceModule with BookRepositoryComponent with Tables with DBConnectionComponent  {
  lazy val dbConnection = new MysqlDBConnection()
  lazy val bookRepository = new DefaultBookRepository()
  lazy val booksService = new DefaultBookService()
}