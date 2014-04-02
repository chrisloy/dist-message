package hungryworker

import com.zink.queue.ConnectionFactory


object SenderCollector extends App{

  val fileName = "/Users/loyc01/Desktop/access.log"
  val lines: Iterator[String] = scala.io.Source.fromFile(fileName).getLines()

  val con = ConnectionFactory.connect()

  val wc = con.publish("Work")
  wc.write("Hello")

  val times = 100

  val linesList = lines.toList

  1 to times foreach { _ =>
    for (line <- linesList) {
      wc.write(line)
    }
  }
  wc.write("")

  val rc = con.subscribe("Ack")

  var replies = 0

  def read(): Unit = {
    replies += 1
    if (replies % 100 == 0) {
      print('.')
    }
    if (replies < times * 11) {
      read()
    }
  }

  val before = System.currentTimeMillis
  read()
  val after = System.currentTimeMillis

  println(s"Time: ${after - before}ms")
}
