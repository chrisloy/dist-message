package hungryworker

import com.zink.queue._
import scala.concurrent._
import ExecutionContext.Implicits.global

object Worker extends App {

  val workers = 2

  val fs = 1 to workers map { x =>
    future {
      new Worker(x).run()
    }
  }

  Thread.sleep(24 * 60 * 60 * 1000)
}

class Worker(id: Int) {

  val con = ConnectionFactory.connect()
  val rc = con.subscribe("Work")
  val wc = con.publish("Ack")

  def reply(): Unit = {
    wc.write(s"[worker $id] Found one!")
  }

  def run(): Unit = {
    rc.read().toString match {
      case m if m contains "DanielBaudSkiGuide" => reply(); run()
      case _ => run()
    }
  }
}
