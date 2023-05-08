FROM apache/airflow:2.3.3-python3.8

#USER root
#
#RUN mkdir -p /datalake
#RUN chmod 0775 /datalake/

USER airflow

COPY requirements.txt .
RUN pip install -r requirements.txt
