package br.com.experian.driver

import br.com.experian.config.Config
import br.com.experian.config.spark.SparkConfig
import br.com.experian.constants.{ParquetConstants}
import org.apache.log4j.Logger
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.{col}


object IngestionSilver {

  @transient private val logger: Logger = Logger.getLogger(IngestionSilver.getClass)

  def main(args: Array[String]): Unit = {

    try {

      if (args.length <  1) {
        throw new Exception("Variável data (YYYY-MM-DD) não informado." )
      }

      val inputData = args(0)

      if (inputData.length !=  10) {
        throw new Exception("Parâmetro de data (YYYY-MM-DD) inválido." )
      }

      // instanciando arquivo de configuração
      val conf = Config()

      // criando instancia sparkStreamingContext
      val ssc = SparkConfig.createSparkStreamingContext(SparkConfig.configurationSparkStreaming("Ingestion Silver"))
      val sc = ssc.sparkContext
      val sparkSession2 = SparkSession.builder().config(sc.getConf).getOrCreate()
      val broadcastSparkSession = sc.broadcast(sparkSession2)
      val sparkSession = broadcastSparkSession.value

      // Realiza Leitura na camada Bronze basedo no parâmetro de data informado.

      val df = sparkSession.read
              .parquet(ParquetConstants.PATH_TABLE_BRONZE)
              .where(col("data").equalTo(inputData))

      df.show(10, false)

      // Realiza Escrita na camada Silver
      df.write.partitionBy("data")
        .mode(SaveMode.Append)
        .parquet(ParquetConstants.PATH_TABLE_SILVER)

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
