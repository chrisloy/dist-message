package dag

import scala.concurrent._

object Main {

  val workers = 2

  val fs = 1 to workers map { x =>
    future {
      new Worker(x).run()
    }
  }

  Thread.sleep(24 * 60 * 60 * 1000)
}
