package com.github.himeraoO.textms.repository;

import com.github.himeraoO.textms.model.Texts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TextsRepository extends JpaRepository<Texts, Long> {
    List<Texts> findAllByUsername(String query);
    Optional<Texts> findByUsernameAndTitleAndDescription(String username, String title, String description);
}
