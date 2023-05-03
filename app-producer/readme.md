# Objetivo

Esse projeto tem como objetivo produzir eventos através do endPoint (**_api/producer_**) e realizar o envio para um Tópico Kafka

## Solução
![img.png](img.png)

## Tecnologias utilizadas
  Para o desenvolvimento dessa aplicação foram usadas as seguintes tecnlogias:
  - Python
  - Flask

## Documentação

Para acessar a documentação clique [aqui](https://app.swaggerhub.com/apis/hitallow/crud-users/1.0)


## Executar localmente
Para executar o código local, é necessário seguir dois passos.
Mas primeiro crie um arquivo `.env` com base no `.env.example` e preencha as informações, ao mudar a porta onde a aplicação será servida é preciso ter atenção na hora da execução.

### Utilizando venv

É preciso ter instalado o pip na sua máquina. Para instalar a virtualenv você pode usar este comando, caso você utilize uma versão diferente é preciso especificar.
```
  $ pip install virtualenv
```
Agora você pode criar sua virtualenv com o comando a seguir.
```
  $ virtualenv <nome_da_sua_venv> 
```
Após criar sua virtualenv ative ela executando.
```
  $ source <nome_da_sua_venv>/bin/activate
```
Utilize o arquivo `requirements.txt` para utilizar as memas dependencias que eu utilizei. Para isto rode:
```
  $ pip install requirements.txt
```
Rode a aplicação executando:
```
  $ python main.py
```

Após executar este comando basta acessar seu localhost na porta que você especificou no arquivo `.env`, mas caso não tenha alterado ou tenha deixado vazio a porta padrão é a porta 8081.


Para desativar a virtualenv utilize:
```
  $ deactivate
```


### Utilizando docker

Tenha o docker instalado na sua máquina.
Builde a imagem com o seguinte comando no meso nível do arquivo Dockerfile:
```
  $ docker build -t test-hitallo-backend-py:latest .
```
Para então executar o container, utilize: (altere a porta 8081 para a porta que você desejar)
```
  $ docker run -d --name test-hitallo-backend-py  -p 8081:8081 test-hitallo-backend-py
```

Levando em conta que nós deixamos a porta do `.env` como 8081 o código deverá funcionar como mágica ✨.

Caso você tenha escolhido uma porta diferente é preciso alterar o mapeamento para onde foi especifidado.
Neste caso siga este comando personalizado:
```
  $ docker run -d --name test-hitallo-backend-py  -p <host_port>:<container_port> test-hitallo-backend-py
```