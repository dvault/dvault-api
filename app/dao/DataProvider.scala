package it.dvault
package dao


import scala.slick.session.Session

trait DataProvider {
  def users(implicit session: Session): UsersDAO
}

object DefaultDataProvider extends DataProvider {
  def users(implicit session: Session) = new ImplicitSession with DefaultUsersDAO {override val implicitSession = session}
  def usersClients(implicit session: Session) = new ImplicitSession with DefaultUsersClientsDAO {override val implicitSession = session}
}


