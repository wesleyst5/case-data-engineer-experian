package br.com.experian.streaming.constants

object ParquetConstants extends Serializable {

  private val PATH_TABLES = "/dataLake/"
  val TABLE_BRONZE = "bronze"
  val TABLE_SILVER = "silver"
  val TABLE_GOLD = "gold"
  val TABLE_CHECKPOINT = "checkpointData"

  def PATH_TABLE_BRONZE: String = PATH_TABLES.concat(TABLE_BRONZE)
  def PATH_TABLE_SILVER: String = PATH_TABLES.concat(TABLE_SILVER)
  def PATH_TABLE_GOLD: String = PATH_TABLES.concat(TABLE_GOLD)
  def PATH_TABLE_CHECKPOINT: String = PATH_TABLES.concat(TABLE_CHECKPOINT)

}
