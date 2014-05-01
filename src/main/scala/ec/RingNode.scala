package ec

import java.util.HashMap
import java.util.Map

class RingNode(val name: String) {

  private val map: Map[String, AnyRef] = new HashMap[String, AnyRef]

  def put(key: String, value: AnyRef) = map.put(key, value)

  def get(key: String): AnyRef = map.get(key)

  def size: Int = map.size

  override def toString = s"$name -> $size"
}