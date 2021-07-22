package br.com.darchanjo.examples.adapter;

import br.com.darchanjo.examples.model.App;
import br.com.darchanjo.examples.dto.AppDto;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@UtilityClass
public class AppAdapter {

    private ModelMapper modelMapper = new ModelMapper();

    public static App toModel(AppDto dto) {
        App model = modelMapper.map(dto, App.class);
        return model;
    }

    public static Optional<AppDto> toDto(App model) {
        AppDto dto = modelMapper.map(model, AppDto.class);
        return Optional.of(dto);
    }

    public static App mapToModel(AppDto dto, App model) {
        modelMapper.map(dto, model);
        return model;
    }
}
