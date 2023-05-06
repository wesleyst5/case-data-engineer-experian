### VARIAVEIS
export APP_HOME=/app

DATA=$1

if [ -z "$DATA" ];
  then
    echo "Necessário informar uma data no formato YYYY-MM-DD "
    exit 1
fi

#### COMANDOS
nohup spark-submit \
--class br.com.experian.driver.IngestionSilver \
--name '[INGESTION-SILVER] - Batch ' \
--master yarn \
--jars $(echo /app/jars/*.jar | tr ' ' ',') \
/app/app.jar "$DATA" \
> $APP_HOME/ingestionSilver.out &

