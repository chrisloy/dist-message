package day4

import ec.PNCounter
import chrisloy.json.{JsonNumber, JsonObject, Json}

object Movie {

  def fromJson(s: String) = {
    val js = Json.parse(s).asInstanceOf[JsonObject]
    Movie(
      id = js.fields("id").asInstanceOf[JsonNumber].value.toLong,
      rating = PNCounter.fromJson(js.fields("rating").asInstanceOf[JsonObject])
    )
  }
}

case class Movie(id: Long, rating: PNCounter) {

  def toJson: String = {
    s"""{"id":$id,"rating":${rating.toJson}"""
  }
}