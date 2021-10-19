package br.com.example.davidarchanjo.application;

import br.com.example.davidarchanjo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "br.com.example.davidarchanjo")
@SpringBootApplication(scanBasePackages = "br.com.example.davidarchanjo")
public class CRUDRestApplication implements CommandLineRunner {

  @Autowired
  private AppService service;

  public static void main(String[] args) {
    SpringApplication.run(CRUDRestApplication.class, args);
  }

  @Override
  public void run(String... args) {
    service.populate();
  }
}
