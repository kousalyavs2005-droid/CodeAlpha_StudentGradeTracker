#!/usr/bin/env bash
set -e

echo "Compiling Student Grade Tracker..."
mkdir -p out
javac -d out \
  src/com/studentgradetracker/Main.java \
  src/com/studentgradetracker/model/*.java \
  src/com/studentgradetracker/repository/*.java \
  src/com/studentgradetracker/service/*.java \
  src/com/studentgradetracker/controller/*.java \
  src/com/studentgradetracker/view/*.java \
  src/com/studentgradetracker/util/*.java

echo "Starting application..."
java -cp out com.studentgradetracker.Main
