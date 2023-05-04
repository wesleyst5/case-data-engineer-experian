from airflow import DAG
from datetime import datetime, timedelta
from airflow.operators.python import PythonOperator
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator

def print_message():
    print('Init consummer kafka....')

SCHEDULE_INTERVAL = None   # '@once', '@daily'

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2023, 5, 3),
    'retries': 0,
    'catchup': False,
    'retry_delay': timedelta(minutes=5),
}

dag=DAG('run_spark_scala_job_bronze',
          default_args=default_args,
          schedule_interval=SCHEDULE_INTERVAL,
          )

python_operator=PythonOperator(task_id='print_message_task',
                                 python_callable=print_message, dag=dag)

spark_config = {
    'conf': {
        "spark.yarn.maxAppAttempts": "1",
        "spark.yarn.executor.memoryOverhead": "512"
    },
    'conn_id': 'spark_local',
    'java_class': 'br.com.experian.driver.Main',
    'application': '/opt/airflow/app/consumer-kafka-1.0.0.jar',
    'driver_memory': '1g',
    'executor_cores': 1,
    'num_executors': 1,
    'executor_memory': '1g'
}

spark_operator = SparkSubmitOperator(task_id='spark_scala_consumer_job_spark_submit_task', dag=dag, **spark_config)

python_operator >> spark_operator
