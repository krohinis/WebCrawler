package controllers

import akka.actor.{Actor, ActorRef, Props}
import controllers.CrawlServer.{CrawlRequest, CrawlResponse}
import controllers.LinkChecker.Result

import scala.collection.mutable

/**
  * Created by synerzip on 14/4/17.
  */
object CrawlServer {
  case class CrawlRequest(url: String, depth: Integer) {}
  case class CrawlResponse(url: String, links: Set[String]) {}
}

class CrawlServer extends Actor {
  val clients: mutable.Map[String, Set[ActorRef]] = mutable.Map[String, Set[ActorRef]]()
  val controllers: mutable.Map[String, ActorRef] = mutable.Map[String, ActorRef]()

  def receive = {
    case CrawlRequest(url, depth) =>
      val controller = controllers get url
      if (controller.isEmpty) {
        controllers += (url -> context.actorOf(Props(new LinkChecker(url, depth))))
        clients += (url -> Set.empty[ActorRef])
      }
      clients(url) += sender

    case Result(url, links) =>
      context.stop(controllers(url))
      clients(url) foreach (_ ! CrawlResponse(url, links))
      clients -= url
      controllers -= url
  }
}