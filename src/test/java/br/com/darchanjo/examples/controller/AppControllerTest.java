package br.com.darchanjo.examples.controller;

import br.com.darchanjo.examples.dto.AppDto;
import br.com.darchanjo.examples.exception.AppNotFoundException;
import br.com.darchanjo.examples.service.AppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.darchanjo.examples.utils.AppUtils.createAppDto;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AppController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppService service;

    @Captor
    private ArgumentCaptor<AppDto> argumentCaptor;

    private final static String urlTemplate = "/api/v1/apps";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    @DisplayName("Should Create New App Successfully")
    public void shouldCreateNewAppSuccessfully() throws Exception {
        Optional<AppDto> dto = Optional.of(createAppDto("awesome-app", "1.0.0", "Java Duke"));

        when(service.createNewApp(argumentCaptor.capture()))
            .thenReturn(1L);

        mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(header().string("Location", "http://localhost/api/v1/apps/1"));

        assertThat(argumentCaptor.getValue().getName(), is("awesome-app"));
        assertThat(argumentCaptor.getValue().getAuthor(), is("Java Duke"));
        assertThat(argumentCaptor.getValue().getVersion(), is("1.0.0"));
    }

    @Test
    @Order(2)
    @DisplayName("Should Get All Apps With Array Of Two Items Successfully")
    public void shouldGetAllAppsAndWithArrayOfTwoItemsSuccessfully() throws Exception {
        List<Optional<AppDto>> stub = Arrays.asList(
            createAppDto("github", "1.3.7", "Java Duke"),
            createAppDto("linkedin", "1.8", "Java Duke"))
            .stream()
            .map(Optional::of)
            .collect(Collectors.toList());

        when(service.getAllApps())
            .thenReturn(stub);

        mockMvc.perform(get(urlTemplate))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].appName", is("github")))
            .andExpect(jsonPath("$[0].devName", is("Java Duke")))
            .andExpect(jsonPath("$[1].appName", is("linkedin")))
            .andExpect(jsonPath("$[1].appVersion", is("1.8")));
    }

    @Test
    @Order(3)
    @DisplayName("Should Get App By Id Successfully")
    public void shouldGetAppByIdOneSuccessfully() throws Exception {
        Optional<AppDto> stub = Optional.of(createAppDto("facebook", "1.0.0-SNAPSHOT", "Java Duke"));

        when(service.getAppById(1L))
            .thenReturn(stub);

        mockMvc.perform(get(urlTemplate + "/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.appName", is("facebook")))
            .andExpect(jsonPath("$.appVersion", is("1.0.0-SNAPSHOT")))
            .andExpect(jsonPath("$.devName", is("Java Duke")));
    }

    @Test
    @Order(4)
    @DisplayName("Should Get AppNotFoundException With Error Not Found Http Status")
    public void shouldGetAppNotFoundExceptionWithErrorNotFoundHttpStatus() throws Exception {
        when(service.getAppById(1L))
            .thenThrow(new AppNotFoundException("No such App for id '1'"));

        mockMvc.perform(get(urlTemplate + "/1"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    @DisplayName("Should Update App With Known IdSuccessfully")
    public void shouldUpdateAppWithKnownIdSuccessfully() throws Exception {
        Optional<AppDto> stub = Optional.of(createAppDto("twitter", "0.1.0", "Java Duke"));
        AppDto dto = createAppDto("twitter", "0.1.0", "Java Duke");

        when(service.updateApp(eq(1L), argumentCaptor.capture()))
            .thenReturn(stub);

        mockMvc.perform(put(urlTemplate + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isNoContent());

        assertThat(argumentCaptor.getValue().getName(), is("twitter"));
        assertThat(argumentCaptor.getValue().getAuthor(), is("Java Duke"));
        assertThat(argumentCaptor.getValue().getVersion(), is("0.1.0"));
    }

    @Test
    @Order(6)
    @DisplayName("Should Try Update App With Unknown Id With Error NotFound Http Status")
    public void shouldTryUpdateAppWithUnknownIdWithErrorNotFoundHttpStatus() throws Exception {
        AppDto dto = createAppDto("pinterest", "2.0.1-RELEASE", "Java Duke");

        when(service.updateApp(eq(42L), argumentCaptor.capture()))
            .thenThrow(new AppNotFoundException("No such App for id '42'"));

        mockMvc.perform(put(urlTemplate + "/42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

}
