package br.com.example.davidarchanjo.application;

import br.com.example.davidarchanjo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "br.com.example.davidarchanjo")
@SpringBootApplication(scanBasePackages = "br.com.example.davidarchanjo")
@EnableJpaRepositories(basePackages = "br.com.example.davidarchanjo")
public class CRUDRestApplication implements CommandLineRunner {

  @Autowired
  private AppService service;

  public static void main(String[] args) {
    SpringApplication.run(CRUDRestApplication.class, args);
  }

  /**
   * The database is being populated from here because Spring Boot will
   * automatically call the run method of all beans implementing
   * CommandLineRunner interface after the application context has been loaded.
   **/
  @Override
  public void run(String... args) {
    service.populate();
  }
}
