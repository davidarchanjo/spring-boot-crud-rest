package br.com.example.davidarchanjo.repository;

import br.com.example.davidarchanjo.model.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {
}
