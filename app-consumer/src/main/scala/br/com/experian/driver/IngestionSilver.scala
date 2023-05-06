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

      val sparkSession = SparkSession.builder()
        .config(SparkConfig.configurationSparkBatch("Ingestion Silver")).getOrCreate();

      // Realiza Leitura na camada Bronze basedo no parâmetro de data informado.
      val df = sparkSession.read
              .parquet(ParquetConstants.PATH_TABLE_BRONZE)
              .where(col("data").equalTo(inputData))

      df.show(10, false)

      // Realiza Escrita na camada Silver
      df.write.partitionBy("data")
        .mode(SaveMode.Append)
        .parquet(ParquetConstants.PATH_TABLE_SILVER)

      System.out.println("Ingestion Realizado com sucesso.")

    } catch {
      case e: Exception =>
        logger.error(e)
        System.out.println("Erro na execução: " + e)
    }

  }


}
