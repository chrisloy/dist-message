package ec

import scala.collection.mutable
import chrisloy.json.{JsonObject, JsonNumber, Json}

object GCounter {

  def fromJson(js: JsonObject) = {
//    val js: JsonObject = Json.parse(s).asInstanceOf[JsonObject]
    val map: Map[String, Long] = js.fields map {
      case (k, v: JsonNumber) => k -> v.value.toLong
    }
    new GCounter(mutable.Map(map.toSeq: _*))
  }
}

class GCounter(var map: mutable.Map[String, Long] = mutable.Map()) {

  def inc(node: String): Unit = inc(node, 1L)

  def inc(node: String, long: Long): Unit = {
    val existing: Long = getForNode(node)
    map.put(node, long + existing)
  }

  def value = map.values.sum

  def merge(that: GCounter): GCounter = {
    val tuples =
      for (node <- this.keys ++ that.keys)
      yield node -> math.max(this.getForNode(node), that.getForNode(node))
    tuples foreach { case (node, num) => map.put(node, num) }
    this
  }

  def keys: Set[String] = map.keys.toSet

  def getForNode(name: String) = map.get(name) getOrElse 0L

  override def toString = s"${value.toString} $map"

  val toJson: String = {
    val m: Map[String, JsonNumber] = map.toMap map {case (k, v) => k -> Json(v)}
    Json(m).render
  }
}
