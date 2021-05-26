import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success}

object PromiseStdIn extends App {

  def applyFromStdIn(lineInputProcessor: Int => Unit): Unit = {
    lineInputProcessor(io.StdIn.readLine().toInt)
  }

  val future: Future[Int] = {
    val promise: Promise[Int] = Promise[Int]
    this.applyFromStdIn(i => promise.success(7 * i))
    promise.future
  }

  future onComplete {
    case Success(value) => println(value)
    case Failure(throwable) => throwable.printStackTrace()
  }
  Await.result(future, Duration.Inf)
}