package br.com.experian.config.spark

import br.com.experian.config.Config
import br.com.experian.constants.SparkConstants
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkConfig {

  def configurationSparkStreaming(appName: String = ""/*, config: Config*/): SparkConf = {
    val conf = new SparkConf().setAppName(appName)

    val config = Config()
    conf.set(SparkConstants.SPARK_SERIALIZER, config.getString(SparkConstants.SPARK_SERIALIZER_VALUE))
    conf.set(SparkConstants.SPARK_KYOSERIALIZER_BUFFER_MAX, config.getString(SparkConstants.SPARK_KYOSERIALIZER_BUFFER_MAX_VALUE))
    conf.set(SparkConstants.SPARK_SQL_CBO_ENABLED, config.getString(SparkConstants.SPARK_SQL_CBO_ENABLED_VALUE))
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    conf.set("spark.sql.hive.convertMetastoreParquet", "false") // Uses Hive SerDe, this is mandatory for MoR tables
    conf.setMaster(config.getString(SparkConstants.SPARK_MASTER_NAME_VALUE))

    //conf.set("spark.network.timeout", "600s")

    conf
  }

  def configurationSparkBatch(appName: String = "" /*, config: Config*/): SparkConf = {
    val conf = new SparkConf().setAppName(appName)

    val config = Config()
    conf.set(SparkConstants.SPARK_SERIALIZER, config.getString(SparkConstants.SPARK_SERIALIZER_VALUE))
    conf.set(SparkConstants.SPARK_KYOSERIALIZER_BUFFER_MAX, config.getString(SparkConstants.SPARK_KYOSERIALIZER_BUFFER_MAX_VALUE))
    conf.set(SparkConstants.SPARK_SQL_CBO_ENABLED, config.getString(SparkConstants.SPARK_SQL_CBO_ENABLED_VALUE))
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//    conf.set("spark.sql.hive.convertMetastoreParquet", "false") // Uses Hive SerDe, this is mandatory for MoR tables
    conf.setMaster(config.getString(SparkConstants.SPARK_MASTER_NAME_VALUE))

    //conf.set("spark.network.timeout", "600s")

    conf
  }

  def createSparkStreamingContext(conf: SparkConf): StreamingContext = {
    new StreamingContext(conf, Seconds(2))
  }


}
