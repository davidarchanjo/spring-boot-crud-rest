package br.com.darchanjo.examples.utils;

import br.com.darchanjo.examples.dto.AppDto;

public class AppUtils {

    public static AppDto createAppDto(String name, String version, String author) {
        AppDto dto = new AppDto();
        dto.setName(name);
        dto.setVersion(version);
        dto.setAuthor(author);
        return dto;
    }
}
