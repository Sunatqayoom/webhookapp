package com.bajaj.webhookapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Define a RestTemplate bean
	@Bean
	@Lazy
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@PostConstruct
	public void init() {

		generateWebhook();
	}

	// Method to call /generateWebhook
	private void generateWebhook() {
		String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook";

		// Prepare the request body
		String requestBody = "{\n" +
				"  \"name\": \"Sunat Qayoom\",\n" +
				"  \"regNo\": \"RA2211003020003\",\n" +
				"  \"email\": \"sq0691@srmist.edu.in\"\n" +
				"}";

		// Set up the request headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);


		System.out.println("Response from generateWebhook: " + response.getBody());


		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonResponse = mapper.readTree(response.getBody());

			String webhookUrl = jsonResponse.path("webhook").asText();
			String accessToken = jsonResponse.path("accessToken").asText();

			System.out.println("Webhook URL: " + webhookUrl);
			System.out.println("Access Token: " + accessToken);

			sendToTestWebhook(webhookUrl, accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to send result to the testWebhook
	private void sendToTestWebhook(String webhookUrl, String accessToken) {

		String resultBody = "{ \"outcome\": [[1, 2]] }";  // Example result

		// Set up the headers, including the JWT access token (without Bearer prefix)
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", accessToken); // Removed the 'Bearer' prefix
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(resultBody, headers);

		// Retry logic (up to 4 attempts)
		int attempts = 0;
		boolean success = false;
		while (attempts < 4 && !success) {
			try {
				// Make the POST request to the testWebhook URL
				ResponseEntity<String> testWebhookResponse = restTemplate.exchange(webhookUrl, HttpMethod.POST, entity, String.class);


				if (testWebhookResponse.getStatusCode().is2xxSuccessful()) {
					success = true;
					System.out.println("Successfully sent data to webhook.");
				} else {
					throw new Exception("Failed to send data, status code: " + testWebhookResponse.getStatusCode());
				}
			} catch (Exception e) {
				attempts++;
				System.out.println("Attempt " + attempts + " failed: " + e.getMessage());
				if (attempts == 4) {
					System.out.println("All attempts failed.");
				}
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {
		// optional
	}
}
