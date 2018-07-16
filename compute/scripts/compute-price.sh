#!/bin/bash

metric=$1
numTrades=$2
numSplits=$3

appJAR='compute-engine-spark-0.1.0.jar'
dockerImg='amolthacker/qlib'

spark-submit --master yarn \
             --conf spark.executorEnv.YARN_CONTAINER_RUNTIME_TYPE=docker \
             --conf spark.executorEnv.YARN_CONTAINER_RUNTIME_DOCKER_IMAGE=$dockerImg \
             --conf spark.executorEnv.YARN_CONTAINER_RUNTIME_DOCKER_MOUNTS=/etc/passwd:/etc/passwd:ro \
             --conf spark.executor.memory=4g \
             --conf spark.executor.extraLibraryPath=/usr/local/lib \
             --supervise --driver-memory 4g --driver-library-path /usr/local/lib \
             --class com.hwx.pe.valengine.spark.Valengine $appJAR $metric $numTrades $numSplits