import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Random, Success}

object CompositeFutureChallenge extends App {

  val random = new Random()
  val f1 = Future {
    random.nextInt(10)
  }
  val f2 = Future {
    random.nextInt(10)
  }
  val f3 = Future {
    random.nextInt(10)
  }
  val f4 = Future {
    random.nextInt(10)
  }

  val compositeFuture: Future[Int] = for {
    w <- f1
    x <- f2
    y <- f3
    z <- f4
  } yield w * x * y * z

  compositeFuture onComplete {
    case Success(value) => println(value)
    case Failure(throwable) => throwable.printStackTrace()
  }
  Await.result(compositeFuture, Duration.Inf)
}