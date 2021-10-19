package br.com.example.davidarchanjo;

import br.com.example.davidarchanjo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The database is being populated from here because Spring Boot will
 * automatically call the run method of all beans implementing 
 * CommandLineRunner interface after the application context has been loaded.
 **/
@Component
public class AppInitializer implements CommandLineRunner {

    @Autowired
    private AppService service;

    @Override
    public void run(String... args) {
        service.populate();
    }
}