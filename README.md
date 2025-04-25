🏁 Overview
This Spring Boot application is built for the Bajaj Finserv Health Programming Challenge. It performs the following on startup:

Calls a webhook URL

Solves one of the assigned problems based on the registration number

Sends the result back using JWT authentication

📁 Main Application Files
webhookapp/src/main/java/com/bajaj/webhookapp/Application.java
→ Entry point of the Spring Boot application. Handles startup logic and calls the webhook.

webhookapp/src/main/java/com/bajaj/webhookapp/MutualFollowers.java
→ Contains logic for solving the "Mutual Followers" problem.

📦 JAR File
The executable .jar file is available in the "release" folder.

To run the application:
java -jar release/webhookapp.jar


🖼 Output Screenshot
:

![Screenshot 2025-04-25 223350](https://github.com/user-attachments/assets/94de9172-9504-46aa-86db-45cfd4120d18)

![Screenshot 2025-04-25 190100](https://github.com/user-attachments/assets/4be42e51-9b48-40af-8b5f-74232b1dc313)

