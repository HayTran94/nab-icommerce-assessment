# Start Elasticsearch connector

curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://52.77.250.133:8083/connectors/ -d @elasticsearch-sink.json

# Start MySQL connector

curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://52.77.250.133:8083/connectors/ -d @mysql-source.json
