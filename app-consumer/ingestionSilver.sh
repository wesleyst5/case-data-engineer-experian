### VARIAVEIS
export PATH_HOME=/app
export PATH_LIBS=$PATH_HOME/jars

DATA=$1

if [ -z "$DATA" ];
  then
    echo "Necessário informar uma data no formato YYYY-MM-DD "
    exit 1
fi

#### COMANDOS
spark-submit \
--class br.com.experian.driver.IngestionSilver \
--name '[INGESTION-SILVER] - Batch ' \
--master yarn \
--jars $(echo $PATH_LIBS/*.jar | tr ' ' ',') \
$PATH_HOME/app.jar "$DATA"

