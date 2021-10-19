package br.com.example.davidarchanjo.utils;

import br.com.example.davidarchanjo.model.dto.AppDTO;

public class AppUtils {

    public static AppDTO createAppDto(String name, String version, String author) {
        return AppDTO.builder()
            .name(name)
            .version(version)
            .author(author)
            .build();
    }
}
