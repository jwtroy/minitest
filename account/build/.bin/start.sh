#!/bin/sh
cd `dirname "$0"`
java -jar app/{project.name}-{project.version}.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/