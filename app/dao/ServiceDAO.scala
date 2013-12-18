package it.dvault
package dao

import scala.concurrent.Future
import org.joda.time.DateTime

import models.Service
import dao.utils.JodaDateTimeMapper.Implicits._
import dao.utils.MapStringString.Implicits._

trait ServicesDAO extends CrudDAO[Service] {
  this: ImplicitSession =>
}

trait DefaultServicesDAO extends ServicesDAO {
  this: ImplicitSession =>

  val table = ServicesDAO.services

  def create(service: Service): Future[Service] = ???
  def update(service: Service): Future[Service] = ???

}

object ServicesDAO {

  class Services extends TableWithId[Service]("services") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def categoryId = column[Int]("categoryId", O.NotNull)
    def parentServiceId = column[Int]("parentServiceId")
    def url = column[String]("url", O.NotNull)
    def secret = column[Array[Byte]]("secret", O.NotNull)
    def created = column[DateTime]("created", O.NotNull)
    def metadata = column[Map[String, String]]("metadata")
    def notes = column[String]("notes")

    def * = id.? ~ categoryId ~ parentServiceId.? ~ url ~ secret ~ created ~ metadata ~ notes <> (Service.apply _, Service.unapply _)
  }

  val services = new Services
}



