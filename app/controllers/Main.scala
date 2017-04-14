package controllers

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import controllers.CrawlServer.{CrawlRequest, CrawlResponse}

/**
  * Created by synerzip on 14/4/17.
  */
object Main extends App {
  println(s"Current Time ${System.currentTimeMillis}")
  val system = ActorSystem("Crawler")
  val receptionist = system.actorOf(Props[CrawlServer], "CrawlServer")
  val main = system.actorOf(Props(new Main(receptionist, "https://www.google.co.in/", 2)), "BBCActor")

}

class Main(receptionist: ActorRef, url: String, depth: Integer) extends Actor {
  receptionist ! CrawlRequest(url, depth)

  def receive = {
    case CrawlResponse(root, links) =>
      println("3")
      println(s"Root: $root")
      println(s"Links: ${links.toList.sortWith(_.length < _.length).mkString("\n")}")
      println("=========")
      println(s"Current Time ${System.currentTimeMillis}")
  }
}