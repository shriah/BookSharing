package models

case class AuthorsRow(id: Option[Int], name: String = "", description: Option[String] = None, media: Option[String] = None)