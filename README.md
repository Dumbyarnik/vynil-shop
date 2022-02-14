1) Starten des Programmes Docker Desktop

2) Den Befehl "./mvnw package " ausführen

3) Dann den Befehl: " docker build -f src/main/docker/Dockerfile.jvm -t quarkus code-with-quarkus-jvm ." ausführen

4) Dann den Befehl: " ./mvnw compile quarkus:dev" ausführen

Das Testen der API ist möglich mit Swagger UI
Der Security Teil wurde umgesetzt mit: HTTP basic authentication

Zwei Nutzer zum Einloggen vorgefertigt:
1) Als normaler Nutzer - username: frank, password: frank
1) Als Admin - username: admin, password: admin

Web Applikation erreichbar über die Startseite
localhost:8080/template/genre
Von dieser Startseite sollten alle anderen Pages navigierbar sein.

Das Testen ist möglich mit dem Befehl: "mvn verify"