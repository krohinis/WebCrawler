import play.Project._

name := "hello-play-scala"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.2.2", 
  "org.webjars" % "bootstrap" % "2.3.1"
)

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "com.ning" % "async-http-client" % "1.9.40"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.3"


resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

playScalaSettings
