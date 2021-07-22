package br.com.darchanjo.examples.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AppDto {

    @NotEmpty
    @Size(max = 20)
    @JsonProperty("appName")
    private String name;

    @NotEmpty
    @JsonProperty("appVersion")
    private String version;

    @NotEmpty
    @JsonProperty("devName")
    private String author;
}
