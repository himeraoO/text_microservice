package com.github.himeraoO.textms.service;

import com.github.himeraoO.textms.DTO.TextsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TextsService {
    TextsDTO findTexts(long id);

    List<TextsDTO> findAll();

    Page<TextsDTO> findWithPagination(Integer page, Integer textsPerPage);

    TextsDTO update(long id, TextsDTO textsDTO);

    void delete(long id);

    TextsDTO save(TextsDTO textsDTO);

    List<TextsDTO> findByUsername(String query);
}
