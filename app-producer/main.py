from flask import Flask, jsonify, request, abort
import json
from kafka import KafkaProducer

# creating a Flask app
app = Flask(__name__)

TOPIC_NAME = "TAXIFARE"
KAFKA_SERVER = "localhost:9092"

producer = KafkaProducer(
    bootstrap_servers = KAFKA_SERVER
)

def kafkaProducer(req):
    json_payload = json.dumps(req)
    json_payload = str.encode(json_payload)

    # push data into TAXIFARI TOPIC
    producer.send(TOPIC_NAME, json_payload)
    producer.flush()
    print("Sent to consumer")
    return jsonify({"message": "Sent to consumer", "status": "Pass"})

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
