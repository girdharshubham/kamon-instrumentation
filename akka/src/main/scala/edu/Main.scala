package edu

import kamon.Kamon
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape, Materializer, SourceShape}
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.Future
import scala.concurrent.duration._

object Main {
  def main(args: Array[String]): Unit = {
    Kamon.init

    implicit val system: ActorSystem = ActorSystem("Kamon-Instrumentation")

    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val source = builder.add(Source[Int](1 to 100000))

      val flow = builder.add(Flow[Int].map(_ * 2))

      val throttle = builder.add(Flow[Int].throttle(1, 1.second))

      val sink = builder.add(Sink.foreach(println))

      source ~> flow ~> throttle ~> sink
      ClosedShape
    }).run()
  }
}
