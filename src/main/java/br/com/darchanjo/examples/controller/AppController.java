package br.com.darchanjo.examples.controller;

import br.com.darchanjo.examples.dto.AppDto;
import br.com.darchanjo.examples.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/apps")
public class AppController {

    @Autowired
    private AppService service;

    @PostMapping
    public ResponseEntity<?> createNewApp(@Valid @RequestBody AppDto dto, UriComponentsBuilder uriComponentsBuilder) {
        /**
         * Following RFC standards, we should return a 201 (CREATED) status on successfully storing the request resource.
         * In most of the applications the id of newly created resource is generated, so it is a good practice to return it.
         * To do so, it is a common practice return a URI of the newly created resource int the response's header Location.
         **/
        Long appId = service.createNewApp(dto);
        UriComponents uriComponents = uriComponentsBuilder
            .path("/api/v1/apps/{id}")
            .buildAndExpand(appId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllApps() {
        return ResponseEntity.ok(service.getAllApps());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getAppById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApp(@PathVariable("id") Long id, @Valid @RequestBody AppDto dto) {
        service.updateApp(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApp(@PathVariable("id") Long id) {
        service.deleteAppById(id);
        return ResponseEntity.ok().build();
    }

}
