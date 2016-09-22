package models

import db.DBConnectionComponent

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  this: DBConnectionComponent =>
  val profile = dbConnection.profile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = AuthorBook.schema ++ Authors.schema ++ Books.schema ++ PlayEvolutions.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table AuthorBook
   *  @param bookId Database column book_id SqlType(INT UNSIGNED)
   *  @param authorId Database column author_id SqlType(INT UNSIGNED) */
  case class AuthorBookRow(bookId: Int, authorId: Int)
  /** GetResult implicit for fetching AuthorBookRow objects using plain SQL queries */
  implicit def GetResultAuthorBookRow(implicit e0: GR[Int]): GR[AuthorBookRow] = GR{
    prs => import prs._
    AuthorBookRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table author_book. Objects of this class serve as prototypes for rows in queries. */
  class AuthorBook(_tableTag: Tag) extends Table[AuthorBookRow](_tableTag, "author_book") {
    def * = (bookId, authorId) <> (AuthorBookRow.tupled, AuthorBookRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(bookId), Rep.Some(authorId)).shaped.<>({r=>import r._; _1.map(_=> AuthorBookRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column book_id SqlType(INT UNSIGNED) */
    val bookId: Rep[Int] = column[Int]("book_id")
    /** Database column author_id SqlType(INT UNSIGNED) */
    val authorId: Rep[Int] = column[Int]("author_id")

    /** Primary key of AuthorBook (database name author_book_PK) */
    val pk = primaryKey("author_book_PK", (bookId, authorId))

    /** Foreign key referencing Authors (database name author) */
    lazy val authorsFk = foreignKey("author", authorId, Authors)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Books (database name book) */
    lazy val booksFk = foreignKey("book", bookId, Books)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AuthorBook */
  lazy val AuthorBook = new TableQuery(tag => new AuthorBook(tag))

  /** Entity class storing rows of table Authors
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(200,true), Default()
   *  @param description Database column description SqlType(TEXT), Default(None)
   *  @param media Database column media SqlType(VARCHAR), Length(200,true), Default(None) */
  
  /** GetResult implicit for fetching AuthorsRow objects using plain SQL queries */
  implicit def GetResultAuthorsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[AuthorsRow] = GR{
    prs => import prs._
    AuthorsRow.tupled((<<?[Int], <<[String], <<?[String], <<?[String]))
  }
  /** Table description of table authors. Objects of this class serve as prototypes for rows in queries. */
  class Authors(_tableTag: Tag) extends Table[AuthorsRow](_tableTag, "authors") {
    def * = (id.?, name, description, media) <> (AuthorsRow.tupled, AuthorsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), description, media).shaped.<>({r=>import r._; _1.map(_=> AuthorsRow.tupled((_1, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(200,true), Default() */
    val name: Rep[String] = column[String]("name", O.Length(200,varying=true), O.Default(""))
    /** Database column description SqlType(TEXT), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))
    /** Database column media SqlType(VARCHAR), Length(200,true), Default(None) */
    val media: Rep[Option[String]] = column[Option[String]]("media", O.Length(200,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Authors */
  lazy val Authors = new TableQuery(tag => new Authors(tag))

  /** Entity class storing rows of table Books
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(200,true), Default()
   *  @param edition Database column edition SqlType(VARCHAR), Length(200,true), Default(Some())
   *  @param binding Database column binding SqlType(VARCHAR), Length(200,true), Default(Some())
   *  @param published Database column published SqlType(DATE), Default(None)
   *  @param price Database column price SqlType(DOUBLE UNSIGNED), Default(None)
   *  @param media Database column media SqlType(VARCHAR), Length(200,true), Default(None) */
  /** GetResult implicit for fetching BooksRow objects using plain SQL queries */
  implicit def GetResultBooksRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[java.sql.Date]], e4: GR[Option[Double]]): GR[BooksRow] = GR{
    prs => import prs._
    BooksRow.tupled((<<[Option[Int]], <<[String], <<?[String], <<?[String], <<?[java.sql.Date], <<?[Double], <<?[String]))
  }
  /** Table description of table books. Objects of this class serve as prototypes for rows in queries. */
  class Books(_tableTag: Tag) extends Table[BooksRow](_tableTag, "books") {
    def * = (id.?, name, edition, binding, published, price, media) <> (BooksRow.tupled, BooksRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), edition, binding, published, price, media).shaped.<>({r=>import r._; _1.map(_=> BooksRow.tupled((_1, _2.get, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(200,true), Default() */
    val name: Rep[String] = column[String]("name", O.Length(200,varying=true), O.Default(""))
    /** Database column edition SqlType(VARCHAR), Length(200,true), Default(Some()) */
    val edition: Rep[Option[String]] = column[Option[String]]("edition", O.Length(200,varying=true), O.Default(Some("")))
    /** Database column binding SqlType(VARCHAR), Length(200,true), Default(Some()) */
    val binding: Rep[Option[String]] = column[Option[String]]("binding", O.Length(200,varying=true), O.Default(Some("")))
    /** Database column published SqlType(DATE), Default(None) */
    val published: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("published", O.Default(None))
    /** Database column price SqlType(DOUBLE UNSIGNED), Default(None) */
    val price: Rep[Option[Double]] = column[Option[Double]]("price", O.Default(None))
    /** Database column media SqlType(VARCHAR), Length(200,true), Default(None) */
    val media: Rep[Option[String]] = column[Option[String]]("media", O.Length(200,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Books */
  lazy val Books = new TableQuery(tag => new Books(tag))

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id SqlType(INT), PrimaryKey
   *  @param hash Database column hash SqlType(VARCHAR), Length(255,true)
   *  @param appliedAt Database column applied_at SqlType(TIMESTAMP)
   *  @param applyScript Database column apply_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None)
   *  @param revertScript Database column revert_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None)
   *  @param state Database column state SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param lastProblem Database column last_problem SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String] = None, revertScript: Option[String] = None, state: Option[String] = None, lastProblem: Option[String] = None)
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(VARCHAR), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at SqlType(TIMESTAMP) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script", O.Length(16777215,varying=true), O.Default(None))
    /** Database column revert_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script", O.Length(16777215,varying=true), O.Default(None))
    /** Database column state SqlType(VARCHAR), Length(255,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true), O.Default(None))
    /** Database column last_problem SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem", O.Length(16777215,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))
}
