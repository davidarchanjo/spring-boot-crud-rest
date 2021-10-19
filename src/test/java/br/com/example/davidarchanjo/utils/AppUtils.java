package br.com.example.davidarchanjo.utils;

import br.com.example.davidarchanjo.model.dto.AppDTO;

public class AppUtils {

    public static AppDTO createAppDto(String name, String version, String author) {
        AppDTO dto = new AppDTO();
        dto.setName(name);
        dto.setVersion(version);
        dto.setAuthor(author);
        return dto;
    }
}
