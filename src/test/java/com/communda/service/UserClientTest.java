package com.commonda.service;


import com.commonda.model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration
class UserClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserClientTest.class);

    private static final String successfulJson = """
            {
                "page": 2,
                "per_page": 6,
                "total": 12,
                "total_pages": 2,
                "data": [
                    {
                        "id": 7,
                        "email": "michael.lawson@reqres.in",
                        "first_name": "George",
                        "last_name": "Lawson",
                        "avatar": "https://reqres.in/img/faces/7-image.jpg"
                    }
                ]
            }
            """.replaceAll("\\n", "");

    private static MockWebServer mockServer;
    @Autowired
    private UserClient client;

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
        LOG.info("Current mock server - {}", mockServer.url("/"));
        MockResponse mockResponse = new MockResponse().setResponseCode(200)
                .setBody(successfulJson)
                .addHeader("Content-Type", "application/json");
        mockServer.enqueue(mockResponse);
        mockResponse = new MockResponse().setResponseCode(200)
                .setBody(successfulJson.replace("George", "Thomas"))
                .addHeader("Content-Type", "application/json");
        mockServer.enqueue(mockResponse);
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry r) throws IOException {
        r.add("http.endpoint", () -> mockServer.url("/").toString());
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @Test
    @Order(1)
    @DisplayName("First name of the first user was expected as George")
    public void testGetUsers() {
        List<User> users = client.getUsers();
        Optional<User> fnGeorge = users.stream().findFirst();
        assertThat(fnGeorge).isNotEmpty();
        boolean firstNameShouldBeGeorge = fnGeorge.map(user -> user.getFirstName()).filter(fn -> "George".equals(fn)).isPresent();
        assertThat(firstNameShouldBeGeorge).isEqualTo(true);
    }

    @Test
    @Order(2)
    @DisplayName("First name of the first user should not be George")
    public void testGetUsersWithoutGeorge() {
        List<User> users = client.getUsers();
        Optional<User> fnGeorge = users.stream().findFirst();
        assertThat(fnGeorge).isNotEmpty();
        boolean firstNameShouldBeGeorge = fnGeorge.map(user -> user.getFirstName()).filter(fn -> "George".equals(fn)).isPresent();
        assertThat(firstNameShouldBeGeorge).isEqualTo(false);
    }


}