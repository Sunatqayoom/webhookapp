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

![Screenshot 2025-04-25 200239](https://github.com/user-attachments/assets/6b08de75-4fc5-458d-9166-f523dfa4c389)
