package com.hwx.pe.valengine.spark

import org.apache.spark.sql.SparkSession

import scala.math.{min, random}


object Valengine {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      //.master("local")
      .appName("Valengine")
      .getOrCreate()
    var metric = ValMetric.getRandom
    var numTrades = 1000
    var numBatches = 50
    try {
      if(args.length >=2){
          metric = ValMetric.valueOf(args(0))
          numTrades = args(1).toInt
          if(args.length == 3){
            numBatches = args(2).toInt
          }
      }
    } catch {
      case e: Exception =>
        println("Invalid args")
        println(usage())
        System.exit(1)
    }
    println(s"Calculating Agg $metric for $numTrades Trades ...")
    val aggMetric = spark.sparkContext.parallelize(1 until numTrades, numBatches).map { i =>
      computeMetric(metric)
    }.mean()
    println(s"Agg $metric : $aggMetric")
    spark.stop()
  }

  def computeMetric(metric: ValMetric): Double = {
    metric match {
      case ValMetric.FwdRate  => Pricer.computeFRASpot()
      case ValMetric.NPV      => Pricer.computeSwapNPV()
      case ValMetric.OptionPV => Pricer.computeEquityOptionNPV()
      case _ => 0.0d
    }
  }

  def usage(): String = s"Usage: ./bin/spark-submit --class com.hwx.pe.valengine.spark.Valengine --master local[N] --driver-library-path /usr/local/lib compute-engine-spark-0.1.0.jar <Metric> <NumTrades> [<NumBatches>]"

}
