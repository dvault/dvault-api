name := "dvault-api"

version := "1.0-SNAPSHOT"

play.Project.playScalaSettings

resolvers += "Play new validation api" at "https://raw.github.com/baloo/r/new_validation_api/snapshots"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/repo"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  //"com.typesafe.play" %% "play-datacommons" %  "2.2-SNAPSHOT",
  "com.typesafe.play" %% "play-slick" % "0.5.0.8",
  "postgresql" % "postgresql" % "9.2-1002.jdbc4",
  "org.xerial" % "sqlite-jdbc" % "3.7.2"
)

routesImport += "it.dvault.controllers.utils.RouterExt._"

routesImport += "java.util.UUID"

