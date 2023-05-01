package com.github.himeraoO.textms.service.impl;

import com.github.himeraoO.textms.DTO.TextsDTO;
import com.github.himeraoO.textms.exception.NotFoundException;
import com.github.himeraoO.textms.model.Texts;
import com.github.himeraoO.textms.repository.TextsRepository;
import com.github.himeraoO.textms.service.TextsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class TextsServiceImpl implements TextsService {

    private final TextsRepository textsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TextsServiceImpl(TextsRepository textsRepository, ModelMapper modelMapper) {
        this.textsRepository = textsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TextsDTO findTexts(long id) {
        Texts texts = textsRepository.findById(id).orElseThrow(() -> new NotFoundException("Нет элементов с таким id: " + id));

        return modelMapper.map(texts, TextsDTO.class);
    }

    @Override
    public List<TextsDTO> findAll() {
        List<Texts> textsList = textsRepository.findAll();

        return textsList.stream().map(texts -> modelMapper.map(texts, TextsDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Page<TextsDTO> findWithPagination(Integer page, Integer textsDTOPerPage) {
        return textsRepository.findAll(PageRequest.of(page - 1, textsDTOPerPage)).map(texts -> modelMapper.map(texts, TextsDTO.class));
    }

    @Override
    @Transactional
    public TextsDTO update(long id, TextsDTO textsDTO) {
        textsDTO.setId(id);
        Texts texts = modelMapper.map(textsDTO, Texts.class);

        return modelMapper.map(textsRepository.save(texts), TextsDTO.class);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Texts texts = textsRepository.findById(id).orElseThrow(() -> new NotFoundException("Нет элементов с таким id: " + id));
        textsRepository.delete(texts);
    }

    @Override
    @Transactional
    public TextsDTO save(TextsDTO textsDTO) {
        Texts texts = modelMapper.map(textsDTO, Texts.class);

        return modelMapper.map(textsRepository.save(texts), TextsDTO.class);
    }

    @Override
    public List<TextsDTO> findByUsername(String query) {
        List<Texts> textsList = textsRepository.findAllByUsername(query);

        return textsList.stream().map(texts -> modelMapper.map(texts, TextsDTO.class)).collect(Collectors.toList());
    }
}
