package it.dvault
package dao.utils


import org.joda.time.DateTime

import scala.slick.session.PositionedResult
import scala.slick.session.PositionedParameters
import scala.slick.driver.BasicProfile
import scala.slick.lifted.BaseTypeMapper
import scala.slick.lifted.TypeMapperDelegate

object JodaDateTimeMapper extends BaseTypeMapper[DateTime] {
  def fromSqlType(t: java.sql.Timestamp): DateTime =
    if (t == null) null else new DateTime(t.getTime)

  def toSqlType(t: DateTime): java.sql.Timestamp =
    if (t == null) null else new java.sql.Timestamp(t.getMillis)

  def apply(v1: BasicProfile): TypeMapperDelegate[DateTime] = DateTimeTypeMapper


  object DateTimeTypeMapper extends TypeMapperDelegate[DateTime] {
      def zero = new DateTime(0L)

      def sqlType = java.sql.Types.TIMESTAMP
      def sqlTypeName = "TIMESTAMP"

      def setValue(v: DateTime, p: PositionedParameters) =
        p.setTimestamp(toSqlType(v))
      def setOption(v: Option[DateTime], p: PositionedParameters) =
        p.setTimestampOption(v.map(toSqlType))
      def nextValue(r: PositionedResult) =
        fromSqlType(r.nextTimestamp)
      def updateValue(v: DateTime, r: PositionedResult) =
        r.updateTimestamp(toSqlType(v))

  }
  object Implicits {
    implicit val jodaDateTimeMapper = JodaDateTimeMapper
  }
}

object MapStringString extends BaseTypeMapper[Map[String, String]] {
  def fromSqlType(t: java.sql.Blob): Map[String, String] = ???

  def toSqlType(t: Map[String, String]): java.sql.Blob = ???

  def apply(v1: BasicProfile): TypeMapperDelegate[Map[String, String]] = MapTypeMapper


  object MapTypeMapper extends TypeMapperDelegate[Map[String, String]] {
      def zero = Map.empty

      def sqlType = java.sql.Types.BLOB
      def sqlTypeName = "BLOB"

      def setValue(v: Map[String, String], p: PositionedParameters) =
        p.setBlob(toSqlType(v))
      def setOption(v: Option[Map[String, String]], p: PositionedParameters) =
        p.setBlobOption(v.map(toSqlType))
      def nextValue(r: PositionedResult) =
        fromSqlType(r.nextBlob)
      def updateValue(v: Map[String, String], r: PositionedResult) =
        r.updateBlob(toSqlType(v))

  }
  object Implicits {
    implicit val mapStringString = MapStringString
  }
}

