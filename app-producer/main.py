import os
from dotenv import load_dotenv
from flask import Flask, jsonify, request, abort
import json
from kafka import KafkaProducer

# creating a Flask app
app = Flask(__name__)
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

@app.route('/api/producer', methods=['POST'])
def postMessage():
    data = request.get_json()
    if data is None:
        return jsonify({'error': 'Missing input'}), 400

    kafkaProducer(data)
    return data, 201

# driver function
if __name__ == "__main__":
    app.run(debug=True)
