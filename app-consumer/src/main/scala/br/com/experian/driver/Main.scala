package br.com.experian.driver

import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types._
import org.apache.spark.sql.streaming.Trigger

object Main extends App {

  val IP = "localhost"
  val TOPIC = "TAXIFARE"
  val path = "./"


  val spark = SparkSession
    .builder()
    .appName("kafka-consumer")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  spark.sparkContext.setLogLevel("WARN")

  val ds1 = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", IP + ":9092")
    .option("zookeeper.connect", IP + ":2181")
    .option("subscribe", TOPIC)
    .option("startingOffsets", "earliest")
    .option("max.poll.records", 10)
    .option("failOnDataLoss", false)
    .load()

  ds1.printSchema()

  val schema = StructType(Seq(
    StructField("key", StringType, true),
    StructField("fare_amount", StringType, true),
    StructField("pickup_datetime", StringType, true),
    StructField("pickup_longitude", StringType, true),
    StructField("pickup_latitude", StringType, true),
    StructField("dropoff_longitude", StringType, true),
    StructField("dropoff_latitude", StringType, true),
    StructField("passenger_count", StringType, true)
  ))

  val df = ds1.selectExpr("cast (value as string) as json")
    .select(from_json($"json", schema=schema).as("data")).select("data.*")


  /**
   * To write data in parquet to a directory
   */
//      val query = df.writeStream
//        .option("checkpointLocation", path + "/checkpointData")
//        .format("parquet")
//        .start(path + "/dataParquet")

  /**
   * To check the data in your console
   */
  val query = df.writeStream
    .outputMode("append")
    .queryName("table")
    .format("console")
    .option("truncate", false)
    .trigger(Trigger.Once)      // <-- Gives MicroBatchExecution
    .start()

  query.awaitTermination()

  //spark.sql("select * from table").show(truncate = false)
  //val x = spark.sql("select * from table")

  spark.stop()

}
