package controllers

import java.net.URL

import akka.actor.{Actor, Status}
import org.jsoup.Jsoup

import scala.util._
import scala.collection.JavaConverters._

/**
  * Created by synerzip on 14/4/17.
  */
object Getter {
  case class Done() {}
}

class Getter(url: String, depth: Int) extends Actor {

  import Getter._
  implicit val ec = context.dispatcher

  val currentHost = new URL(url).getHost
  WebClient.get(url) onComplete {
    case Success(body) => self ! body
    case Failure(err) => self ! Status.Failure(err)
  }

  def getAllLinks(content: String): Iterator[String] = {
    Jsoup.parse(content, this.url).select("a[href]").iterator().asScala.map(_.absUrl("href"))
  }

  def receive = {
    case body: String =>
      getAllLinks(body)
        .filter(link => link != null && link.length > 0)
        .filter(link => !link.contains("mailto"))
        .filter(link =>  currentHost  == new URL(link).getHost)
        .foreach(context.parent ! LinkChecker.CheckUrl(_, depth))
      stop
    case _: Status.Failure => stop()
  }

  def stop(): Unit = {
    context.parent ! Done
    context.stop(self)
  }
}