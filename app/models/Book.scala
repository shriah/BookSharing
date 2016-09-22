package models

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class BooksRow(id: Option[Int], name: String = "", edition: Option[String] = Some(""), binding: Option[String] = Some(""), published: Option[java.sql.Date] = None, price: Option[Double] = None, media: Option[String] = None) {
  def patch(name: Option[String], edition: Option[String] = Some(""), binding: Option[String] = Some(""), published: Option[java.sql.Date] = None, price: Option[Double] = None, media: Option[String] = None): BooksRow =
    this.copy(
        name = name.getOrElse(this.name),
        edition = edition.orElse(this.edition),
        binding = binding.orElse(this.binding),
        published = published.orElse(this.published),
        price = price.orElse(this.price),
        media = media.orElse(this.media)
    )
}

object `BooksRow` {
  implicit def bookReads: Reads[BooksRow] = (
    (JsPath \ "id").readNullable[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "edition").readNullable[String] and
    (JsPath \ "binding").readNullable[String] and
    (JsPath \ "published").readNullable[java.sql.Date] and
    (JsPath \ "price").readNullable[Double] and
    (JsPath \ "media").readNullable[String]
  )(BooksRow.apply _)
  implicit def bookWrites: Writes[BooksRow] = (
    (JsPath \ "id").write[Option[Int]] and
    (JsPath \ "name").write[String] and
    (JsPath \ "edition").write[Option[String]] and
    (JsPath \ "binding").write[Option[String]] and
    (JsPath \ "published").write[Option[java.sql.Date]] and
    (JsPath \ "price").write[Option[Double]] and
    (JsPath \ "media").write[Option[String]]
  )(unlift(BooksRow.unapply))
  
  def tupled = (BooksRow.apply _).tupled
}


