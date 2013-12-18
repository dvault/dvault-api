package it.dvault
package models

import org.joda.time.DateTime

case class Group(
  id: Option[Int],
  name: String,
  created: DateTime
) {
}

case class GroupKey(
  id: Option[Int],
  groupId: Int,
  created: DateTime,
  secret: EncryptedSecret
) {
}

case class GroupService(
  groupKeyId: Int,
  serviceId: Int,
  secret: EncryptedSecret
) {
}

