# Imagem base do Ubuntu 20.04
FROM ubuntu:20.04

# Definir variáveis de ambiente
ENV SPARK_VERSION=3.3.2
ENV SCALA_VERSION=2.12.15
ENV JAVA_VERSION=11

# Instalar dependências
RUN apt-get update && apt-get install -y \
    openjdk-$JAVA_VERSION-jdk \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Baixar e extrair o Apache Spark
RUN wget -qO- https://downloads.apache.org/spark/spark-$SPARK_VERSION/spark-$SPARK_VERSION-bin-hadoop3.tgz | tar xvz -C /opt

# Baixar e instalar o Scala
RUN wget https://downloads.lightbend.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.deb && \
    dpkg -i scala-$SCALA_VERSION.deb

# Configurar variáveis de ambiente do Spark
ENV SPARK_HOME=/opt/spark-$SPARK_VERSION-bin-hadoop3
ENV PATH=$PATH:$SPARK_HOME/bin
ENV HADOOP_CONF_DIR=/etc/hadoop
ENV YARN_CONF_DIR=/etc/hadoop

# Definir diretório de trabalho padrão
WORKDIR $SPARK_HOME

# Expor a porta do Spark (opcional, se necessário)
EXPOSE 4040

RUN mkdir -p /app
RUN mkdir -p /datalake

RUN apt-get update && apt-get install -y vim telnetd iputils-ping coreutils
RUN apt update && apt install telnet mlocate
RUN updatedb

#Add file to image
ADD target/*.jar /app/app.jar
COPY ../jars /app/jars
ADD ../ingestionBronze.sh /app/ingestionBronze.sh
ADD ../ingestionSilver.sh /app/ingestionSilver.sh

RUN chmod 0775 /datalake/
RUN chmod 0775 /app/
RUN chmod a+w+x /app/ingestionBronze.sh
RUN chmod a+w+x /app/ingestionSilver.sh

WORKDIR /app

# Comando padrão ao executar o contêiner
CMD ["spark-shell"]
