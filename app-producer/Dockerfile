FROM python:3.8-slim-buster

LABEL MANTAINER="Wesley Santana Tavares"

WORKDIR /python-docker

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]