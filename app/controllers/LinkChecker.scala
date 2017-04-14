package controllers

import akka.actor.IO.Done
import akka.actor.{Actor, ActorRef, Props}
import controllers.LinkChecker.{CheckUrl, Result}

/**
  * Created by synerzip on 14/4/17.
  */
object LinkChecker {
  case class CheckUrl(url: String, depth: Int){}
  case class Result(url: String, links: Set[String]) {}
}

class LinkChecker(root: String, originalDepth: Integer) extends Actor {
  var cache = Set.empty[String]
  var children = Set.empty[ActorRef]

  self ! CheckUrl(root, originalDepth)
  def receive = {
    case CheckUrl(url, depth) =>
      if (!cache(url) && depth > 0)
        children += context.actorOf(Props(new Getter(url, depth-1)))
      cache += url

    case Done =>
      children -= sender
      if (children.isEmpty) context.parent ! Result(root, cache)
  }
}