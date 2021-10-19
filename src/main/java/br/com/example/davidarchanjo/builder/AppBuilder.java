package br.com.example.davidarchanjo.builder;

import java.util.Optional;

import br.com.example.davidarchanjo.model.domain.App;
import br.com.example.davidarchanjo.model.dto.AppDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppBuilder {

    private final ModelMapper modelMapper;

    public App build(AppDTO dto) {
        App model = modelMapper.map(dto, App.class);
        return model;
    }

    public Optional<AppDTO> build(App domain) {
        AppDTO dto = modelMapper.map(domain, AppDTO.class);
        return Optional.of(dto);
    }

    public App build(AppDTO dto, App domain) {
        modelMapper.map(dto, domain);
        return domain;
    }
}
