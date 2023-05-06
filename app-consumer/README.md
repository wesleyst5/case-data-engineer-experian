# Objetivo

1. Esse projeto tem como objetivo realizar a ingestão de dados brutos na camada bronze do datalake
2. Realizar Filtro nos dados da camada raw e armazenar o resultado na camada silver


## Solução
![](doc/img.png)

## Tecnologias do projeto
- Spark 3.3
- Scala 2.12
- Jdk 11
- Maven 3.8

## Ferramentas usadas no desenvolvimento
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&code=IIC)
- [Docker](https://docs.docker.com/compose/install/)
- [Jdk 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads/)

## Pré requisitos:
1. Subir o serviço do kafka através do [docker-compose.yml](https://github.com/wesleyst5/case-data-engineer-experian/blob/main/docker-compose.yaml) localizado na raiz do repositório
2. Para o funcionamento da aplicação no intellij é necessário adicionar a confiuração de hosts **_/etc/hosts_**
```
127.0.0.1 kafka
```
3. Configurar o arquivo [application.conf](src/main/resources/application.conf) 
4. Gerar o .jar da aplicação na pasta **target**. Sugestões:
#### Shell
```
mvn clean package 
```
#### Intellij
```
clicar duas vezes nao opção package do maven  
```
![img_1.png](doc%2Fimg_1.png)


## Para rodar a solução no docker:

### 1. Realize o build e deploy da aplicação
```
docker compose up -d --build
```

### 2. Abra seu browser e entre no endereço abaixo, onde já será possível ver spark rodando com 1 worker.
```
http://localhost:8080
```

### 3. Acesse o container do spark master
```
docker exec -it app-consumer-spark bash
```

### 4. Execute os arquivo .sh para submeter o job spark

1. Inicia uma aplicação streaming, que tem por objetivo consumir os eventos publicados no tópico **TAXIFARE** kafka e armazena na camada **bronze** (raw) no formato **.parquet** particionado por **pickup_datetime** (yyyy-mm-dd).
```
./ingestionBronze.sh | tee ingestionBronze.out
```

**Obervação:** _Para gerar os eventos use o endpoint (http://127.0.0.1:5000/api/producer), criado na aplicação a [app-produce](https://github.com/wesleyst5/case-data-engineer-experian/tree/main/app-producer)_

#### Saída após start da aplicação:
![img_2.png](doc%2Fimg_2.png)

![img_3.png](doc%2Fimg_3.png)


2. Inicia uma aplicação batch, que tem por objetivo realizar a leitura dos registros da camada bronze e movimentar para a camada silver basedo no parâmetro informado para filtro
```
./ingestionSilver.sh 2033-01-01 | tee ingestionSilver.out
```

**Observaçao:** _para visualizar o log gerado pelos jobs, é possível através dos arquivos .out_

#### Saída após start da aplicação:
![img_4.png](doc%2Fimg_4.png)

![img_5.png](doc%2Fimg_5.png)

**Observaçao:** _foi realizado o mapeamento no arquivo docker-compose o mapeamento do volume referênte a pasta do dataLake

### Referências:

[Config Docker Compose Apache Spark](https://hub.docker.com/r/bitnami/spark/)
