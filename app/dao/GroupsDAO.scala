package it.dvault
package dao

import scala.concurrent.Future
import org.joda.time.DateTime

import models.Group
import dao.utils.JodaDateTimeMapper.Implicits._

trait GroupsDAO extends CrudDAO[Group] {
  this: ImplicitSession =>
}

trait DefaultGroupsDAO extends GroupsDAO {
  this: ImplicitSession =>

  val table = GroupsDAO.groups

  def create(group: Group): Future[Group] = ???
  def update(group: Group): Future[Group] = ???

}

object GroupsDAO {

  class Groups extends TableWithId[Group]("groups") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.NotNull)
    def created = column[DateTime]("created", O.NotNull)

    def * = id.? ~ name ~ created <> (Group.apply _, Group.unapply _)
  }

  val groups = new Groups
}


