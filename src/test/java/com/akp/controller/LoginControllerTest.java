package com.akp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.akp.model.Customer;
import com.akp.model.Region;
import com.akp.model.Role;
import com.akp.model.User;
import com.google.gson.Gson;

/**
 * @author Aashish Patel
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

	public RestTemplate restTemplate;
	@LocalServerPort
	int randomServerPort;
	// Timeout value in milliseconds
	int timeout = 10_000;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate(getClientHttpRequestFactory());
		/*
		 * List<HttpMessageConverter<?>> messageConverters = new
		 * ArrayList<HttpMessageConverter<?>>(); //Add the Jackson Message
		 * converter MappingJackson2HttpMessageConverter converter = new
		 * MappingJackson2HttpMessageConverter();
		 * 
		 * // Note: here we are making this converter to process any kind of
		 * response, // not only application/*json, which is the default
		 * behaviour
		 * converter.setSupportedMediaTypes(Collections.singletonList(MediaType.
		 * ALL)); messageConverters.add(converter);
		 * restTemplate.setMessageConverters(messageConverters);
		 */
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
	public void testLoginLogoutSuccess() throws URISyntaxException, IOException {
		// RestTemplate restTemplate = new RestTemplate();

		String testUserName = "ashish.patel";
		String testPassword = "password";
		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(testUserName, testPassword));

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/rest/login";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Gson g = new Gson();
		User userResult = g.fromJson(result.getBody(), User.class);
		Assert.assertEquals(userResult, getMockUser().get());

		String logoutURL = "http://localhost:" + randomServerPort + "/api/rest/logout";
		URI logoutURI = new URI(logoutURL);
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(testUserName, testPassword));

		ResponseEntity<String> logoutMessage = restTemplate.exchange(logoutURI, HttpMethod.GET, entity, String.class);
		Assert.assertEquals(logoutMessage.getBody(), "{logout:success}");
	}

	HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4551787166212577296L;

			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	private Optional<User> getMockUser() {
		// given
		User user = new User();

		Region region = new Region();
		region.setId("NO");
		region.setDescription("NO_DESC");

		Role role1 = new Role();
		role1.setId(1L);
		role1.setRole("ROLE_ADMIN");

		Role role2 = new Role();
		role2.setId(2L);
		role2.setRole("ROLE_USER");

		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);

		Customer customer = new Customer();
		customer.setFirstName("Ashish");
		customer.setLastName("Patel");
		customer.setAddress("F902 Amanora Hadapsar Pune 411028");
		customer.setId("CUSTID_1");
		customer.setRegion(region);

		user.setActive(1);
		user.setEmail("ashish.patel22@gmail.com");
		user.setId(1L);
		user.setUsername("ashish.patel");
		user.setCustomer(customer);
		user.setRoles(roles);
		user.setCustomer(customer);

		return Optional.of(user);
	}
}
