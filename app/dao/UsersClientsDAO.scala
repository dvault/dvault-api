package it.dvault
package dao

import scala.concurrent.Future

import play.api.db.slick.Config.driver.simple._

import dao.utils.Order
import models.UserClient

trait UsersClientsDAO extends CrudDAO[UserClient] {
  this: ImplicitSession =>
}

trait DefaultUsersClientsDAO extends UsersClientsDAO {
  this: ImplicitSession =>

  val table = UsersClientsDAO.userClients

  def create(user: UserClient): Future[UserClient] = ???
  def update(user: UserClient): Future[UserClient] = ???

}

object UsersClientsDAO {

  class UsersClients extends TableWithId[UserClient]("users_clients") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Int]("userId", O.NotNull)
    def publicKey = column[Array[Byte]]("publicKey", O.NotNull)

    def * = id.? ~ userId ~ publicKey <> (UserClient.apply _, UserClient.unapply _)
  }

  val userClients = new UsersClients
}



