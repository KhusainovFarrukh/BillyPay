cd config-server
mvn clean package -DskipTests

cd ../eureka-server
mvn clean package -DskipTests

cd ../bill
mvn clean package -DskipTests

cd ../stats
mvn clean package -DskipTests

cd ../user
mvn clean package -DskipTests