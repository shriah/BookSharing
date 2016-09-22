package db

import slick.driver.JdbcProfile
import slick.backend.DatabaseConfig
import slick.jdbc.JdbcBackend.Database


trait DBConnectionComponent {
  
  val dbConnection: DBConnection
  
  class MysqlDBConnection extends DBConnection {
    lazy val profile = slick.driver.MySQLDriver
    lazy val config:String = "default"
    lazy val dc = DatabaseConfig.forConfig[JdbcProfile](config)
    
  }
}
trait DBConnection {
   val dc: DatabaseConfig[JdbcProfile]
   val profile: slick.driver.JdbcProfile
   val db = dc.db
}