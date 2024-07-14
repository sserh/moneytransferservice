package ru.raccoon.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.raccoon.moneytransferservice.model.Amount;
import ru.raccoon.moneytransferservice.model.Transfer;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneytransferserviceApplicationTests {

    private static final GenericContainer<?> app = new GenericContainer<>("app:1.1")
            .withExposedPorts(5500);

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @BeforeAll
    public static void setup() {
        app.start();
    }

    @Test
    void checkGetResponseOK() {

        final String baseUrl = "http://localhost:" + port + "/transfer";
        URI uri = URI.create(baseUrl);
        //подготовим валидные данные, должны получить 200
        Transfer transfer = new Transfer(
                "1111111111111111",
                "2222222222222222",
                "08/28",
                "333",
                new Amount(24500, "RUR"));

        HttpEntity<Transfer> request = new HttpEntity<>(transfer);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void checkGetResponseBadRequest() {

        final String baseUrl = "http://localhost:" + port + "/transfer";
        URI uri = URI.create(baseUrl);
        //отправим 13 месяц в сроке действия карты, должны получить 400
        Transfer transfer = new Transfer(
                "1111111111111111",
                "2222222222222222",
                "13/28",
                "333",
                new Amount(24500, "RUR"));

        HttpEntity<Transfer> request = new HttpEntity<>(transfer);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertEquals(400, response.getStatusCode().value());
    }
}
