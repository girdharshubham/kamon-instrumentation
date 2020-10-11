package edu

import com.typesafe.config.ConfigFactory
import kamon.Kamon

import scala.concurrent.{ExecutionContext, Promise}
import scala.io.Source

object Main {
  def blockRunner[A](p: Promise[A])(block: => A): Promise[A] = p success block

  def main(args: Array[String]): Unit = {
    Kamon.init
    lazy val config = ConfigFactory.load()
    val dragonBallDataSet = config.getString("plain.dragon-ball-dataset")

    val dataSet = Source.fromFile(dragonBallDataSet)

    val promiseIterString = Promise[Iterator[String]]()
    blockRunner(promiseIterString)(dataSet.getLines())

    promiseIterString.future.foreach(iterator => iterator.foreach { record =>
      Thread.sleep(1000)
      record.split(",").foreach(s => print(s + " "))
      println
    })(ExecutionContext.Implicits.global)
  }
}
