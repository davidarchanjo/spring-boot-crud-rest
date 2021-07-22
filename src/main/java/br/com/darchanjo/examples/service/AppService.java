package br.com.darchanjo.examples.service;

import br.com.darchanjo.examples.dto.AppDto;

import java.util.List;
import java.util.Optional;

public interface AppService {

    Long createNewApp(AppDto dto);

    List<Optional<AppDto>> getAllApps();

    Optional<AppDto> getAppById(Long id);

    Optional<AppDto> updateApp(Long id, AppDto dto);

    void deleteAppById(Long id);

    void populate();
}
