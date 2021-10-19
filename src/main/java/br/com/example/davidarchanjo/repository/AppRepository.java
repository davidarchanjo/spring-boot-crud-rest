package br.com.example.davidarchanjo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.davidarchanjo.model.domain.App;

public interface AppRepository extends JpaRepository<App, Long> {
}
