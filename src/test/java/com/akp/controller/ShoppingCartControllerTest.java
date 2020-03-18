package com.akp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.akp.model.User;
import com.google.gson.Gson;

/**
 * @author Aashish Patel
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerTest {

	public RestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;
	// Timeout value in milliseconds
	int timeout = 10_000;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate(getClientHttpRequestFactory());
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient());

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
		return client;
	}

	@Test
	public void testAddProduct() throws URISyntaxException, IOException {
		// RestTemplate restTemplate = new RestTemplate();
		String testUserName = "ashish.patel";
		String testPassword = "password";

		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("ashish.patel", "password"));

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/rest/login";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> loginResult = restTemplate.getForEntity(uri, String.class);
		// Verify request succeed
		Assert.assertEquals(200, loginResult.getStatusCodeValue());
		Gson g = new Gson();
		User userResult = g.fromJson(loginResult.getBody(), User.class);
		Assert.assertEquals(testUserName, userResult.getId());

		/* Make REST call to add product */
		final String addProductURL = "http://localhost:" + randomServerPort + "/api/rest/shoppingcart/addProduct/11";
		URI addProductURI = new URI(addProductURL);
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(testUserName, testPassword));

		ResponseEntity<String> addProductResult = restTemplate.exchange(addProductURI, HttpMethod.GET, entity,
				String.class);
		// Verify request succeed
		Assert.assertEquals(200, addProductResult.getStatusCodeValue());

		/* Logout */
		String logoutURL = "http://localhost:" + randomServerPort + "/api/rest/logout";
		URI logoutURI = new URI(logoutURL);
		entity = new HttpEntity<>(createHeaders(testUserName, testPassword));

		ResponseEntity<String> logoutMessage = restTemplate.exchange(logoutURI, HttpMethod.GET, entity, String.class);
		Assert.assertEquals(logoutMessage.getBody(), "{logout:success}");
	}

	HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			/**
			* 
			*/
			private static final long serialVersionUID = -7565049744611341532L;

			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

}
