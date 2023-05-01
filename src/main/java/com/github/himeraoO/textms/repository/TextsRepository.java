package com.github.himeraoO.textms.repository;

import com.github.himeraoO.textms.model.Texts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextsRepository extends JpaRepository<Texts, Long> {
    List<Texts> findAllByUsername(String query);
}
