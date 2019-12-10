import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Random, Success}

object CompositeFutureChallenge extends App {

  val random = new Random()
  val f1 = Future {random.nextInt(10)}
  val f2 = Future {random.nextInt(10)}
  val f3 = Future {random.nextInt(10)}
  val f4 = Future {random.nextInt(10)}

  val compositeFuture: Future[Int] = for {
    v1 <- f1
    v2 <- f2
    v3 <- f3
    v4 <- f4
  } yield v1 * v2 * v3 * v4

  compositeFuture onComplete {
    case Success(value) => println(value)
    case Failure(throwable) => throwable.printStackTrace()
  }
  Await.result(compositeFuture, Duration.Inf)
}
