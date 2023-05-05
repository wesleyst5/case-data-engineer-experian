package br.com.experian.driver

import br.com.experian.config.Config
import br.com.experian.config.spark.SparkConfig
import br.com.experian.constants.{KafkaConstants, ParquetConstants}
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json, substring}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType}

object IngestionBronze {

  @transient private val logger: Logger = Logger.getLogger(IngestionBronze.getClass)

  def main(args: Array[String]): Unit = {

    try {

      // instanciando arquivo de configuração
      val conf = Config()

      // criando instancia sparkStreamingContext
      val ssc = SparkConfig.createSparkStreamingContext(SparkConfig.configurationSparkStreaming("Ingestion Bronze"))
      val sc = ssc.sparkContext
      val sparkSession2 = SparkSession.builder().config(sc.getConf).getOrCreate()
      val broadcastSparkSession = sc.broadcast(sparkSession2)
      val sparkSession = broadcastSparkSession.value

      // obtendo topicos kafka consumer

      val df = sparkSession.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", conf.getString(KafkaConstants.KAFKA_BOOTSTRAP_SERVERS_VALUE))
        .option("subscribe", conf.getString(KafkaConstants.KAFKA_CONSUMER_TOPIC_NAME_JSON_TAXFARE))
        .option("group.id", conf.getString(KafkaConstants.GROUP_ID))
        .option("max.poll.records", 100)
        .option("failOnDataLoss", false)
        .option("startingOffsets", "earliest") // From starting
        .load()

      // define schema mensagem kafka
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

      //extrair mensagem do campo value
      val taxiFareDF = df
        .selectExpr("CAST(value AS STRING)")
        .select(from_json(col("value"), schema).as("data"))
        .select("data.*")


//      taxiFareDF.writeStream
//        .format("console")
//        .outputMode("append")
//        .trigger(Trigger.Once)      // <-- Gives MicroBatchExecution
//        .start()
//        .awaitTermination()

      taxiFareDF
        .withColumn("data",substring(col("pickup_datetime"),1,10))
        .writeStream
        .option("checkpointLocation", ParquetConstants.PATH_TABLE_CHECKPOINT)
        .format("parquet")
        .partitionBy("data")
        .outputMode("append")
//        .trigger(Trigger.Once)      // <-- Gives MicroBatchExecution
        .start(ParquetConstants.PATH_TABLE_BRONZE)
        .awaitTermination()

      ssc.start()
      ssc.awaitTermination()

    } catch {
      case e: Exception =>
        logger.error(e)
    }

  }

  def generatedLogger(msg: String, time: Long, fileName: String): Unit = {
    logger.info("#######MEASURE TIME: " + msg + ": " + + time + ". Arquivo: " + fileName)
  }

  def generatedLogger(msg: String): Unit = {
    logger.info("#######LOG PROCCESS: " + msg)
  }

}
