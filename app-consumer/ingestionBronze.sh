### VARIAVEIS
export APP_HOME=/app

#### COMANDOS
nohup spark-submit \
--class br.com.experian.driver.IngestionBronze \
--name '[INGESTION-BRONZE] - Streaming ' \
--master yarn \
--jars $(echo /app/jars/*.jar | tr ' ' ',') \
/app/app.jar \
> $APP_HOME/ingestionBronze.out &
