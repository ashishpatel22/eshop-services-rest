package com.akp.controller;


import java.net.URI;
import java.net.URISyntaxException;

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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.akp.model.Product;
import com.google.gson.Gson;


/**
 * @author Aashish Patel
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    public RestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;
    //Timeout value in milliseconds
    int timeout = 10_000;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate(getClientHttpRequestFactory());
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();

        clientHttpRequestFactory.setHttpClient(httpClient());

        return clientHttpRequestFactory;
    }

    private HttpClient httpClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        HttpClient client = HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        return client;
    }

    @Test
    public void testProductBrowse() throws URISyntaxException {
        //RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor("ashish.patel", "password"));

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/rest/product/browse";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Gson g = new Gson();
        Product[] productsByRegion = g.fromJson(result.getBody(), Product[].class);
        for (Product product: productsByRegion) {
            /* The user ashish.patel has region "NO" so all the prodoucts must have the same region "NO"*/
            Assert.assertEquals("NO", product.getRegion().getId());
        }
    }
}
