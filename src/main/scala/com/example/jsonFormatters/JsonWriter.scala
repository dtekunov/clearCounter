package com.example.jsonFormatters

import com.example.domain.UserMeta
import org.json4s.{JArray, JObject, JString, JValue}
import org.json4s.jackson.JsonMethods.{compact, pretty, render}

object JsonWriter {
  def format(description: String, result: Option[String]): String = result match {
    case None if description == "pong" =>
      compact(render(JObject(
        "message" -> JString(description)
      )))
  }

  def format(meta: UserMeta): String = {
    pretty(render(JObject(
      "id" -> JString(meta.id.toString),
      "login" -> JString(meta.login),
      "name" -> JString(meta.name),
      "groups" -> JArray(meta.groups.toList.map(elem => JString(elem)))
    )))
  }
}
