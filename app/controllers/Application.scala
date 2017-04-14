package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

object Application extends Controller {


//  def webcrawl = Action.async { request =>
//
//    val jsonRequest = request.body.asJson.get
//    val url = jsonRequest.as[Client]
//    val res=WebClient.get(url.url)
//
//    Future(Ok(Json.toJson("Hi")))
//  }
}