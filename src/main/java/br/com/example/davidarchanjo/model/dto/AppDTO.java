package br.com.example.davidarchanjo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
public class AppDTO {

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
