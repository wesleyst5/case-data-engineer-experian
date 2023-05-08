from airflow import DAG
from datetime import datetime, timedelta
from airflow.operators.bash import BashOperator
from airflow.operators.python import PythonOperator
from airflow.providers.docker.operators.docker import DockerOperator
from airflow.models.param import Param

from docker.types import Mount

def print_message():
    print('Init....')

SCHEDULE_INTERVAL = None   # '@once', '@daily'

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2023, 5, 3),
    'retries': 0,
    'catchup': False,
    'retry_delay': timedelta(minutes=5),
    'tags': ['ingestion datalake Bronze']
}

dag=DAG('run_spark_scala_job_bronze',
        default_args=default_args,
        schedule_interval=SCHEDULE_INTERVAL,

        params={
            "data": Param(
                default="2023-05-07",
                type="string",
                minLength=10,
                maxLenght=10
            )
        }
          )

# init_message=PythonOperator(task_id='print_init_message_task',
#                                  python_callable=print_message, dag=dag)

init_message=BashOperator(task_id='print_init_message_task',
                                 bash_command='echo "Parâmetro : {{ params.data }}"', dag=dag)

finish_message=BashOperator(task_id='print_finish_message_task',
                                 bash_command='echo "Finished message consumption!"', dag=dag)

executar_job_spark_ingestion_bronze = DockerOperator(
        docker_url="unix://var/run/docker.sock",
        command="/app/ingestionBronze.sh ",
        image="app-consumer-spark:latest",
        container_name="run-airflow-bronze",
        auto_remove="True",
        network_mode="external-network",
        mounts=[
            Mount(source='dados-datalake', target='/datalake', type='volume')
        ],
        mount_tmp_dir=False,
        task_id="run_job_consumer_bronze",
        dag=dag,
)

init_message >> executar_job_spark_ingestion_bronze >> finish_message
