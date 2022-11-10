cd config-server
mvn clean package -DskipTests

cd ../eureka-server
mvn clean package -DskipTests

cd ../bill-service
mvn clean package -DskipTests

cd ../stats-service
mvn clean package -DskipTests

cd ../user-service
mvn clean package -DskipTests