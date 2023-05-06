# Projeto
Pipeline de ingestão de dados no datalake

### Definição Estratégica:
- Criação de um datalake
- Implementação de uma aplicação para geração dos eventos
- Ingestão de dados nas camadas bronze e silver do datalake
- Disponibilizar ferramenta para orquestração do fluxo de trabalho

## Arquitetura da Solução
![img.png](img/img.png)


## Executar a solução

### Pré-Requisitos
#### Criar rede externa para uso no docker
```
  docker network create --driver bridge external-network
```
#### Subir seviço do kafka
```
  docker-compose up -d
```

  - [Gerador de eventos](app-producer)
  - [Ingestão de dados](app-consumer) 
  - [Orquestrador do fluxo de trabalho](airflow)
