package br.com.darchanjo.examples.integration;

import br.com.darchanjo.examples.Application;
import br.com.darchanjo.examples.dto.AppDto;
import br.com.darchanjo.examples.utils.AppUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String baseUrl;

    @BeforeEach
    public void setUp(){
        baseUrl = "http://127.0.0.1:" + localServerPort + "/api/v1/apps";
    }

    @Test
    @Order(1)
    @DisplayName("When Get All Apps Should Return Array Of Items Greater Than Zero")
    public void whenGetAllAppsShouldReturnArrayOfItemsGreaterThanZero() {
        ResponseEntity<JsonNode> firstResult = testRestTemplate.getForEntity(baseUrl, JsonNode.class);
        assertThat(firstResult.getBody(), notNullValue());
        assertThat(firstResult.getBody().size(), greaterThan(0));
        assertThat(firstResult.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    @Order(2)
    @DisplayName("When Update Known App Should Confirm Changes")
    public void whenUpdateKnownAppShouldConfirmChanges() {
        AppDto originalDto = testRestTemplate.getForObject(baseUrl + "/1", AppDto.class);
        assertThat(originalDto, notNullValue());

        AppDto dto = AppUtils.createAppDto(originalDto.getName(), "0.2.0-SNAPSHOT", "Java Duke");
        testRestTemplate.put(baseUrl + "/1", dto);

        AppDto updatedDto = testRestTemplate.getForObject(baseUrl + "/1", AppDto.class);
        assertThat(updatedDto, notNullValue());
        assertThat(updatedDto.getName(), is(originalDto.getName())); // both names must be equal since the change was made on the original author and version
        assertThat(updatedDto.getAuthor(), not(originalDto.getAuthor())); // must be different because it was changed
        assertThat(updatedDto.getVersion(), not(originalDto.getVersion())); // must be different because it was changed
    }

    @Test
    @Order(3)
    @DisplayName("When Delete Known App Should Not Be Found Anymore")
    public void whenDeleteKnownAppShouldNotBeFoundAnymore() {
        ResponseEntity<JsonNode> firstResult = testRestTemplate.getForEntity(baseUrl + "/1", JsonNode.class);
        assertThat(firstResult.getBody(), notNullValue());
        assertThat(firstResult.getStatusCode(), is(HttpStatus.OK));

        testRestTemplate.delete(baseUrl + "/1");
        ResponseEntity<JsonNode> secondResult = testRestTemplate.getForEntity(baseUrl + "/1", JsonNode.class);
        assertThat(secondResult.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

}
