package br.com.darchanjo.examples.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.darchanjo.examples.adapter.AppAdapter;
import br.com.darchanjo.examples.dto.AppDto;
import br.com.darchanjo.examples.exception.AppNotFoundException;
import br.com.darchanjo.examples.model.App;
import br.com.darchanjo.examples.repository.AppRepository;
import br.com.darchanjo.examples.service.AppService;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository repository;

    @Override
    public Long createNewApp(AppDto dto) {
        return Stream.of(dto)
            .map(AppAdapter::toModel)
            .map(repository::save)
            .findFirst()
            .get()
            .getId();
    }

    @Override
    public List<Optional<AppDto>> getAllApps() {
        return repository.findAll()
            .stream()
            .map(AppAdapter::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<AppDto> getAppById(Long id) {
        return repository.findById(id)
            .stream()
            .findFirst()
            .map(AppAdapter::toDto)
            .orElseThrow(() -> new AppNotFoundException(String.format("No such App for id '%s'", id)));
    }

    @Transactional
    @Override
    public Optional<AppDto> updateApp(Long id, AppDto dto) {
        return repository.findById(id)
            .stream()
            .findFirst()
            .map(model -> AppAdapter.mapToModel(dto, model))
            .map(repository::save)
            .map(AppAdapter::toDto)
            .orElseThrow(() -> new AppNotFoundException(String.format("No such App for id '%s'", id)));
    }

    @Override
    public void deleteAppById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void populate() {
        final Faker faker = new Faker();
        IntStream.range(0, 100).forEach(i -> {
            App app = new App();
            app.setAuthor(faker.app().author());
            app.setName(faker.app().name());
            app.setVersion(faker.app().version());

            repository.save(app);
        });
    }
}
