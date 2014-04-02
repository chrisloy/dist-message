package hungryworker

import com.zink.queue._
import scala.concurrent._
import ExecutionContext.Implicits.global

object Worker extends App {

  val workers = 3

  val fs = 1 to workers map { x =>
    future {
      new Worker(x).run()
    }
  }

  Thread.sleep(120 * 1000)
}

class Worker(x: Int) {

  val con = ConnectionFactory.connect()
  val rc = con.subscribe("Work")
  val wc = con.publish("Ack")

  def reply(): Unit = {
    wc.write(s"[worker $x] Found one!")
  }

  def run(): Unit = {
    rc.read().toString match {
      case m if m contains "DanielBaudSkiGuide" => reply(); run()
      case _ => run()
    }
  }
}
