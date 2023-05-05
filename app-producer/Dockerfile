FROM alpine:latest

LABEL MANTAINER="Wesley Santana Tavares"

RUN apk update
RUN apk add py-pip
RUN apk add --no-cache bash
RUN apk add busybox-extras
RUN apk add --no-cache python3-dev
RUN pip install --upgrade pip

WORKDIR /app
COPY . /app

RUN rm .env
RUN mv .env.example .env

RUN pip --no-cache-dir install -r requirements.txt

CMD ["python3", "main.py"]