package br.com.experian.constants

object KafkaConstants {

  val KAFKA_BOOTSTRAP_SERVERS_VALUE: String = "kafka.bootstrap.servers.value"
  val BATCH_SIZE_CONFIG_VALUE: String = "kafka.batch.size.config.value"
  val SCHEMA_REGISTRY_URL: String = "schema.registry.url"
  val SCHEMA_REGISTRY_URL_VALUE: String = "kafka.schema.registry.url.value"
  val ACKS_CONFIG_VALUE = "kafka.acks.config.value"
  val MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION_VALUE = "kafka.max_in_flight_requests_per_connection.value"
  val LINGER_MS_CONFIG_VALUE = "kafka.linger_ms.config.value"
  val FETCH_MESSAGE_MAX_BYTES = "fetch.message.max.bytes"
  val FETCH_MESSAGE_MAX_BYTES_VALUE = "kafka.fetch.message.max.bytes.value"
  val MAX_PARTITION_FETCH_BYTES = "max.partition.fetch.bytes"
  val MAX_PARTITION_FETCH_BYTES_VALUE = "kafka.max.partition.fetch.bytes.value"
  val GROUP_ID = "kafka.group.id"

  val KAFKA_CONSUMER_TOPIC_NAME_JSON_TAXFARE = "kafka.topic.name.consumer.json.tax_fare"
}
