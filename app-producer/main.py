# using flask_restful
from flask import Flask, jsonify, request, abort
import json
from kafka import KafkaProducer

# creating a Flask app
app = Flask(__name__)

TOPIC_NAME = "TAXIFARE"
KAFKA_SERVER = "localhost:9092"

producer = KafkaProducer(
    bootstrap_servers = KAFKA_SERVER,
    api_version = (0, 11, 15)
)

def kafkaProducer(req):
    #req = request.get_json()
    json_payload = json.dumps(req)
    json_payload = str.encode(json_payload)

    # push data into TAXIFARI TOPIC
    producer.send(TOPIC_NAME, json_payload)
    producer.flush()
    print("Sent to consumer")
    return jsonify({"message": "Sent to consumer", "status": "Pass"})

@app.route('/api/producer', methods=['POST'])
def postMessage():
    if not request.json or not 'key' in request.json\
                        or not 'fare_amount' in request.json \
                        or not 'pickup_datetime' in request.json:
        abort(400)

    taxiFare = {
        'key': request.json['key'],
        'fare_amount': request.json['fare_amount'],
        'pickup_datetime': request.json['pickup_datetime'],
        'pickup_longitude': request.json['pickup_longitude'],
        'pickup_latitude': request.json['pickup_latitude'],
        'dropoff_longitude': request.json['dropoff_longitude'],
        'dropoff_latitude': request.json['dropoff_latitude'],
        'passenger_count': request.json['passenger_count'],
    }

    kafkaProducer(taxiFare)
    return jsonify(taxiFare), 201

# driver function
if __name__ == "__main__":
    app.run(debug=True)
