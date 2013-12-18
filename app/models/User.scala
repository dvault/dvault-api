package it.dvault
package models

case class User(
  id: Option[Int],
  email: String,
  admin: Boolean
) {
}

case class UserClient(
  id: Option[Int],
  userId: Int,
  publicKey: PublicKey
) {
}

case class UserClientGroupKey(
  userClientId: Int,
  groupKeyId: Int,
  secret: EncryptedSecret
) {
}

