# Objetivo

Esse projeto tem como objetivo produzir eventos através do endPoint (**_api/producer_**) e realizar o envio para um Tópico Kafka

## Solução
![img.png](doc%2Fimg.png)

## Tecnologias utilizadas
  Para o desenvolvimento dessa aplicação foram usadas as seguintes tecnlogias:
  - Python
  - Flask

## Pré requisitos:
1. Subir o serviço do kafka através do [docker-compose.yml](https://github.com/wesleyst5/case-data-engineer-experian/blob/main/docker-compose.yaml) localizado na raiz do projeto

## Para rodar a solução:
### 1. Builde a imagem e execute o comando abaixo para subir a aplicação
```
  docker compose up -d --build
```
### 2. Realize chamada ao endPoint da aplicação. Segue Sugestões abaixo:
#### Curl
```
curl --location 'http://127.0.0.1:5000/api/producer' --header 'Content-Type: application/json' --data '{
 "key": "2023-05-04 17:26:21.0000001",
 "fare_amount": "4.5",
 "pickup_datetime": "2023-05-04 17:00:00",
 "pickup_longitude": "-73.844311",
 "pickup_latitude": "40.721319",
 "dropoff_longitude": "-73.84161",
 "dropoff_latitude": "40.712278",
 "passenger_count": "5"
}'
```
#### Postman
[POC - Serasa.postman_collection.json](doc%2FPOC%20-%20Serasa.postman_collection.json)

## Resultados:

![img_3.png](doc%2Fimg_3.png)

Acesse a url abaixo para visualizar através do client kafdrop o registro enviado ao tópico:
```
http://localhost:19000/
```

![img_1.png](doc%2Fimg_1.png)

![img_2.png](doc%2Fimg_2.png)

