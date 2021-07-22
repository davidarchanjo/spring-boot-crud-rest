package br.com.darchanjo.examples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.darchanjo.examples.model.App;

public interface AppRepository extends JpaRepository<App, Long> {
}
