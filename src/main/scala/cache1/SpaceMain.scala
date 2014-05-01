package cache1

import com.zink.fly.kit.FlyFactory

object SpaceMain {

  case class Movie(
    title: String = null,
    stars: Integer = null,
    rating: String = null,
    narrative: String = null)

  val fly = FlyFactory.makeFly("localhost")

  val movie1 = Movie("Rio 2", 5, "U", "Nice Birds fight bad people")
  val movie2 = Movie("Rio 3", 5, "U", "Nice Birds fight bad people")
  val movie3 = Movie("Rio 4", 5, "U", "Nice Birds fight bad people")

  fly.write(movie1, 60 * 60 * 1000)
  fly.write(movie2, 60 * 60 * 1000)
  fly.write(movie3, 60 * 60 * 1000)

  println(s"Found ${fly.readMany(Movie(stars = 5), 1000)}")
}
