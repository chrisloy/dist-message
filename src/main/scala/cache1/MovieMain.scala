package cache1

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object MovieMain {

  val client = new MovieClient
  val cache = new MyCache

  def time[A](block: => A): A = {
    val before = System.currentTimeMillis
    val result = block
    val after = System.currentTimeMillis
    println(s"Time: ${after - before}ms, Hits ${cache.hits}, Misses ${cache.misses}")
    result
  }

  def doTheThing(): Unit = time {
    val pop = cache.cached("popular", () => client.popular)
    client.getIds(pop)
  } foreach { id =>
    time{
      cache.cached(id, () => client.detailsById(id))
    }
  }

  val futures: Seq[Future[Unit]] = 1 to 10000 map { _ =>
    future {
      doTheThing()
    }
  }

  Await.result(Future.sequence(futures), 120 seconds)
}
