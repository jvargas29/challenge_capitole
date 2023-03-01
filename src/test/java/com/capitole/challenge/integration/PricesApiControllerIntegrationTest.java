package com.capitole.challenge.integration;

import com.capitole.challenge.model.PriceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PricesApiControllerIntegrationTest {

    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate restTemplate;

    private  PriceResponse  expectedTestCase1;
    private  PriceResponse  expectedTestCase2;
    private  PriceResponse  expectedTestCase3;
    private  PriceResponse  expectedTestCase4;
    private  PriceResponse  expectedTestCase5;

    @BeforeEach
    public void setup() throws JsonProcessingException {

        String responseTestCase1= """
                {
                  "productId": "35455",
                  "brandId": "1",
                  "priceListId": "1",
                  "startDate": "2020-06-14T00:00:00Z",
                  "endDate": "2020-12-31T23:59:59Z",
                  "price": 35.5
                }""";

        String responseTestCase2= """
                {
                  "productId": "35455",
                  "brandId": "1",
                  "priceListId": "2",
                  "startDate": "2020-06-14T15:00:00Z",
                  "endDate": "2020-06-14T18:30:00Z",
                  "price": 25.45
                }""";
        String responseTestCase3= """
                {
                  "productId": "35455",
                  "brandId": "1",
                  "priceListId": "1",
                  "startDate": "2020-06-14T00:00:00Z",
                  "endDate": "2020-12-31T23:59:59Z",
                  "price": 35.5
                }""";
        String responseTestCase4= """
                {
                  "productId": "35455",
                  "brandId": "1",
                  "priceListId": "3",
                  "startDate": "2020-06-15T00:00:00Z",
                  "endDate": "2020-06-15T11:00:00Z",
                  "price": 30.5
                }""";
        String responseTestCase5= """
                {
                  "productId": "35455",
                  "brandId": "1",
                  "priceListId": "4",
                  "startDate": "2020-06-15T16:00:00Z",
                  "endDate": "2020-12-31T23:59:59Z",
                  "price": 38.95
                }""";

        this.expectedTestCase1 = mapperToPriceResponse(responseTestCase1);
        this.expectedTestCase2 = mapperToPriceResponse(responseTestCase2);
        this.expectedTestCase3 = mapperToPriceResponse(responseTestCase3);
        this.expectedTestCase4 = mapperToPriceResponse(responseTestCase4);
        this.expectedTestCase5 = mapperToPriceResponse(responseTestCase5);
    }

    @Test
    void testCase1() throws Exception {
        ResponseEntity<PriceResponse> response = getPrices("/prices?dateApplicationPrice=2020-06-14 10:00:00&productId=35455&brandId=1");
        PriceResponse actual = response.getBody() != null ? response.getBody() : null;
        Assertions.assertThat(actual).isEqualTo(this.expectedTestCase1);
    }
    @Test
    void testCase2() throws Exception {
        ResponseEntity<PriceResponse> response = getPrices("/prices?dateApplicationPrice=2020-06-14 16:00:00&productId=35455&brandId=1");
        PriceResponse actual = response.getBody() != null ? response.getBody() : null;
        Assertions.assertThat(actual).isEqualTo(this.expectedTestCase2);
    }
    @Test
    void testCase3() throws Exception {
        ResponseEntity<PriceResponse> response = getPrices("/prices?dateApplicationPrice=2020-06-14 21:00:00&productId=35455&brandId=1");
        PriceResponse actual = response.getBody() != null ? response.getBody() : null;
        Assertions.assertThat(actual).isEqualTo(this.expectedTestCase3);
    }
    @Test
    void testCase4() throws Exception {
        ResponseEntity<PriceResponse> response = getPrices( "/prices?dateApplicationPrice=2020-06-15 10:00:00&productId=35455&brandId=1");
        PriceResponse actual = response.getBody() != null ? response.getBody() : null;
        Assertions.assertThat(actual).isEqualTo(this.expectedTestCase4);
    }

    @Test
    void testCase5() throws Exception {
        ResponseEntity<PriceResponse> response = getPrices("/prices?dateApplicationPrice=2020-06-16 21:00:00&productId=35455&brandId=1");
        PriceResponse actual = response.getBody() != null ? response.getBody() : null;
        Assertions.assertThat(actual).isEqualTo(this.expectedTestCase5);
    }

    private ResponseEntity<PriceResponse> getPrices(String parameters) throws MalformedURLException {
        return restTemplate.getForEntity(
                new URL("http://localhost:" + port + parameters).toString(), PriceResponse.class);
    }

    private PriceResponse mapperToPriceResponse(String responseTestCase1) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        return objectMapper.readValue(responseTestCase1,PriceResponse.class);
    }
}
