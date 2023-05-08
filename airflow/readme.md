# Objetivo

Esse projeto tem por objetivo provisionar uma aplicação airflow e disponibilizar dags para execução dos jobs spark.

## Tecnologias do projeto
  - [Python 3.8](https://www.python.org/downloads/release/python-380/)

## Ferramentas utilizadas
- [PyCharm](https://www.jetbrains.com/pycharm/download/#section=windows)
- [Docker](https://docs.docker.com/compose/install/)


## Prepar ambiente de desenvolvimento:
```
pip install apache-airflow
pip install apache-airflow-providers-docker
```

## Para rodar a solução:
### 1. Prepara variável de projeto para uso do docker-compose
```
cp .env-sample .env
docker-compose up -d --build
```
### 2. Abra seu browser e entre com o seguinte endereço:
```
http://localhost:8090
```
```
Entre com o usuário airflow e a senha airflow
```

## Para rodar a solução:

### 1. Ative a DAG e execute
![img.png](img%2Fimg.png)

***Observação:*** _para execução da DAG run_park_scala_job_silver, é necessário exutar com a opção Trigger DAG w\ config e informar uma data no formato YYYY-MM-DD


### 2. Resultado da execução
![img_1.png](img%2Fimg_1.png)

![img_2.png](img%2Fimg_2.png)


***Observação:*** _caso esteja usando [windows + wsl + docker-desktop], segue o path do datalake:_
```
  \\wsl.localhost\docker-desktop-data\version-pack-data\community\docker\volumes\dados-datalake\_data
```

#### Referências:

[Setting Up Apache Airflow with Docker-Compose in 5 Minutes](https://towardsdatascience.com/setting-up-apache-airflow-with-docker-compose-in-5-minutes-56a1110f4122)



