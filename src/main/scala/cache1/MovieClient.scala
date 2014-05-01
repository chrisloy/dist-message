package cache1

import java.net.{URL, URLConnection}
import java.util.Scanner

class MovieClient {

  val base = "https://api.themoviedb.org/3/"
  val key = "?api_key=89e5511513f926ee8ac9569963afa8f2"
  val idRegex = """"id":(\d+)""".r

  def popular: String = httpGet(base + "movie/popular" + key)

  def getIds(pop: String) = (idRegex findAllIn pop).matchData.map(_.group(1).toString).toSeq

  def detailsById(id: String) = httpGet(base + "movie/" + id + key)

  def httpGet(uri: String): String = {
    val conn: URLConnection = new URL(uri).openConnection
    val scr: Scanner = new Scanner(conn.getInputStream)
    scr.useDelimiter("\\Z")
    val result = scr.next
    result
  }
}


