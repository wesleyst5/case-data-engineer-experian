# Objetivo

Esse projeto tem como objetivo consumir eventos de um tópico Kafka e armazenar em formato Parquet na camada Bronze

## Solução
![img.png](img.png)

## Tecnologias utilizadas
Para o desenvolvimento dessa aplicação foram usadas as seguintes tecnlogias:
- Spark
- Scala
- Kafka

## Documentação

Para acessar a documentação clique [aqui](https://app.swaggerhub.com/apis/hitallow/crud-users/1.0)

## Executar localmente
Para executar o código local, é necessário seguir dois passos.
Mas primeiro crie um arquivo `.env` com base no `.env.example` e preencha as informações, ao mudar a porta onde a aplicação será servida é preciso ter atenção na hora da execução.

### Arquivo de Configuração

O arquivo [application.conf](https://app.swaggerhub.com/apis/hitallow/crud-users/1.0) contem todas as configurações para os ambientes local e dev
