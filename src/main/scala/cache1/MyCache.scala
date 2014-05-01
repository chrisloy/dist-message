package cache1

import com.zink.cache.{CacheFactory, Cache}
import java.util.concurrent.atomic.AtomicInteger

class MyCache {

  val (hits, misses) = (new AtomicInteger, new AtomicInteger)

  private val cache: Cache = CacheFactory.connect

  def cached[A](key: String, block: () => A): A = try {
    Option(cache.get(key)) match {
      case Some(value) =>
        hits.incrementAndGet
        value.asInstanceOf[A]
      case None =>
        misses.incrementAndGet
        val value = block()
        cache.setnx(key, value.asInstanceOf[java.io.Serializable])
        cache.expire(key, 1000 * 6)
        value
    }
  } catch {
    case e: NullPointerException =>
      println("ERROR")
      block()
  }
}
