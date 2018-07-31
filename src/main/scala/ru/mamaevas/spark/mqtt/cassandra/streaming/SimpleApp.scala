package ru.mamaevas.spark.mqtt.cassandra.streaming

import com.datastax.spark.connector.{SomeColumns, _}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

object SimpleApp {
  implicit val formats1 = DefaultFormats // Brings in default date formats etc.
  implicit val formats2 = Serialization.formats(NoTypeHints)

  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("spark-mqtt-cassandra-streaming")
    sparkConf.set("spark.cassandra.connection.host", "192.168.0.1")
    sparkConf.set("spark.cassandra.auth.username", "user")
    sparkConf.set("spark.cassandra.auth.password", "password")

    // create Streaming Context
    val ssc = new StreamingContext(sparkConf, Seconds(60))
    val mqttStream = MQTTUtils.createStream(ssc,"tcp://'brokerUrl_here'", "topic", StorageLevel.MEMORY_ONLY_SER_2, Some("clientId"), Some("username"), Some("password"), Some(false), Some(0), Some(0), Some(60), Some(4))
    mqttStream.foreachRDD {
      rdd => {
        println("v.35--------------------------------------------------------")
        val x = rdd.collect().map(r => {
          val json = parse(r.toString)
          val measureBean = json.extract[MeasureBean]
          val markersJson = write(measureBean.markers)
          Tuple3(measureBean.device_id, measureBean.timedate, markersJson)
        })
        val collection = ssc.sparkContext.parallelize(x.toSeq)
        collection.saveToCassandra("keyspaceName", "tableName", SomeColumns("device_id", "timedate", "markers"))
      }
    }
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
