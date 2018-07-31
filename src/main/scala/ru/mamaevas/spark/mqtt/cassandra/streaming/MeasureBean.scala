package ru.mamaevas.spark.mqtt.cassandra.streaming

case class MeasureBean(device_id: String, timedate: Long, markers: Seq[MarkerBean]) {}
