package dag

import com.zink.queue.ConnectionFactory


class Sender(name: String) {

  val fileName = "/Users/loyc01/Desktop/access-huge.log"
  val lines: Iterator[String] = scala.io.Source.fromFile(fileName).getLines()

  val con = ConnectionFactory.connect()

  val wc = con.publish("Work")

  val before = System.currentTimeMillis

  for (line <- lines) {
    wc.write(line)
  }
  wc.write("")


  var replies = 0

  def read(): Unit = {
    replies += 1
    if (replies % 100 == 0) {
      print('.')
    }
    if (replies < 5313) {
      read()
    }
  }

  read()
  val after = System.currentTimeMillis

  println(s"Time: ${after - before}ms to find $replies")
}
