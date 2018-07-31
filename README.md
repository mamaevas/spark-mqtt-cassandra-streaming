<h2>Simple app for getting data from mqtt and saving it to cassandra</h2>  
  
Default spark settings:  
spark UI port - 4040, 8080  
  
all needed libraries(dependencies) may save into spark/jars directory or download it via command line with argument --package  
  
start locally   
./bin/spark-submit --class ru.mamaevas.spark.mqtt.cassandra.streaming.SimpleApp --master local[*] spark-mqtt-cassandra-streaming_2.11-1.0.jar  
  
start debug mode  
./bin/spark-submit --class ru.mamaevas.spark.mqtt.cassandra.streaming.SimpleApp --deploy-mode client spark-mqtt-cassandra-streaming_2.11-1.0.jar  
  
start production mode  
./bin/spark-submit --class ru.mamaevas.spark.mqtt.cassandra.streaming.SimpleApp --master spark://_host:port_ --deploy-mode cluster --supervise spark-mqtt-cassandra-streaming_2.11-1.0.jar  



