package br.com.example.davidarchanjo.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "applications")
public class App {

  @Id
  @GeneratedValue
  private Long id;
  private String author;
  private String name;
  private String version;

}
