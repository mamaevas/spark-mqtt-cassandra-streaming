lazy val root = (project in file(".")).
  settings(
    name := "spark-mqtt-cassandra-streaming",
    version := "1.0",
    scalaVersion := "2.11.8",
    mainClass in Compile := Some("ru.mamaevas.spark.mqtt.cassandra.streaming.SimpleApp")
  )
val sparkVersion = "2.2.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion, // spark runtime already provides jars
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.bahir" %% "spark-streaming-mqtt" % "2.2.0"
)

//resolvers += "Spark Packages Repo" at "https://dl.bintray.com/spark-packages/maven"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.1"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.5.3"

