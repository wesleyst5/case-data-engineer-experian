### VARIAVEIS
export PATH_HOME=/app
export PATH_LIBS=$PATH_HOME/jars

#### COMANDOS
spark-submit \
--class br.com.experian.driver.IngestionBronze \
--name '[INGESTION-BRONZE] - Streaming ' \
--master yarn \
--jars $(echo $PATH_LIBS/*.jar | tr ' ' ',') \
$PATH_HOME/app.jar
