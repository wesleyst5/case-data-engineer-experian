import os
from dotenv import load_dotenv
from flask import Flask, jsonify, request
from flask_expects_json import expects_json
import json
import datetime
from kafka import KafkaProducer

# creating a Flask app
app = Flask(__name__)
app.config["DEBUG"] = True
app.config['JSON_AS_ASCII'] = False

load_dotenv()

topic = os.getenv('TOPIC_NAME', 'TAXIFARE')
broker_kafka = os.getenv('KAFKA_SERVER', 'localhost:9092')

producer = KafkaProducer(
    bootstrap_servers = broker_kafka
)

def kafkaProducer(req):
    json_payload = json.dumps(req)
    json_payload = str.encode(json_payload)

    # push data into TAXIFARI TOPIC
    producer.send(topic, json_payload)
    producer.flush()
    print("Sent to consumer")
    return jsonify({"message": "Sent to consumer", "status": "Pass"})

@app.errorhandler(404)
def page_not_found(e):
    return {'message': 'Route not found'}, 404


schema = {
    'type': 'object',
    'properties': {
        'key': {'type': 'string'},
        'fare_amount': {'type': 'string'},
        'pickup_datetime': {'type': 'string'},
        'pickup_longitude': {'type': 'string'},
        'pickup_latitude': {'type': 'string'},
        'dropoff_longitude': {'type': 'string'},
        'dropoff_latitude': {'type': 'string'},
        'passenger_count': {'type': 'string'}
    },
    'required': ['key', 'fare_amount', 'pickup_datetime', 'pickup_longitude', 'pickup_latitude', 'dropoff_longitude', 'dropoff_latitude', 'passenger_count']
}

@app.route('/api/producer', methods=['POST'])
@expects_json(schema)
def postMessage():
    print('Iniciando registro de eventos...')
    data = request.get_json()

    try:
        datetime_str = data['pickup_datetime']
        date_time_obj_pickup_datetime = datetime.datetime.strptime(datetime_str, '%Y-%m-%d %H:%M:%S')
    except Exception as e:
        print('Invalid Format Date (YYYY-MM-DD HH24:MM:SS) pickup_datetime : Missing input')
        return jsonify({'Invalid Format Date (YYYY-MM-DD HH24:MM:SS) pickup_datetime ': 'Missing input'}), 400


    print('Send register to topic ' + topic + ' the broker ' + broker_kafka)
    kafkaProducer(data)

    return data, 201

# driver function
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
