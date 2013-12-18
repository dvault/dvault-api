package it.dvault
package dao

import scala.concurrent.Future
import org.joda.time.DateTime

import models.GroupKey
import dao.utils.JodaDateTimeMapper.Implicits._

trait GroupsKeysDAO extends CrudDAO[GroupKey] {
  this: ImplicitSession =>
}

trait DefaultGroupsKeysDAO extends GroupsKeysDAO {
  this: ImplicitSession =>

  val table = GroupsKeysDAO.groups

  def create(group: GroupKey): Future[GroupKey] = ???
  def update(group: GroupKey): Future[GroupKey] = ???

}

object GroupsKeysDAO {

  class GroupsKeys extends TableWithId[GroupKey]("groups") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def groupId = column[Int]("groupId", O.NotNull)
    def created = column[DateTime]("created", O.NotNull)
    def secret = column[Array[Byte]]("secret", O.NotNull)

    def * = id.? ~ groupId ~ created ~ secret <> (GroupKey.apply _, GroupKey.unapply _)
  }

  val groups = new GroupsKeys
}



