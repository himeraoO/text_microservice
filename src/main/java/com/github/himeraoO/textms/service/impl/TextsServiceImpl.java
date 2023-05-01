package com.github.himeraoO.textms.service.impl;

import com.github.himeraoO.textms.DTO.TextsDTO;
import com.github.himeraoO.textms.exception.ConflictException;
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
        Texts texts = textsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет элементов с таким id: " + id));

        return modelMapper.map(texts, TextsDTO.class);
    }

    @Override
    public List<TextsDTO> findAll() {
        List<Texts> textsList = textsRepository.findAll();

        if (textsList.isEmpty()) {
            throw new NotFoundException("Элементы не найдены");
        }

        return textsList.stream()
                .map(texts -> modelMapper.map(texts, TextsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<TextsDTO> findWithPagination(Integer page, Integer textsDTOPerPage) {
        Page<TextsDTO> dtoPage = textsRepository.findAll(PageRequest.of(page - 1, textsDTOPerPage))
                .map(texts -> modelMapper.map(texts, TextsDTO.class));

        if((dtoPage.getTotalElements() == 0) || (page > dtoPage.getTotalPages())){
            throw new NotFoundException("Элементы не найдены");
        }

        return dtoPage;
    }

    @Override
    @Transactional
    public TextsDTO update(long id, TextsDTO textsDTO) {
        Texts textsFromBd = textsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет элементов с таким id: " + id));

        textsFromBd.setUsername(textsDTO.getUsername());
        textsFromBd.setTitle(textsDTO.getTitle());
        textsFromBd.setDescription(textsDTO.getDescription());
        textsFromBd.setData(textsDTO.getData());

        return modelMapper.map(textsRepository.save(textsFromBd), TextsDTO.class);
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
        Texts textsFromBd = textsRepository
                .findByUsernameAndTitleAndDescription(
                        textsDTO.getUsername(),
                        textsDTO.getTitle(),
                        textsDTO.getDescription())
                .orElseThrow(() -> new ConflictException(
                        String.format(
                                "Элементы с такими значениями: username: %s, title: %s, desription: %s уже существуют: ",
                                textsDTO.getUsername(),
                                textsDTO.getTitle(),
                                textsDTO.getDescription())));

        Texts texts = modelMapper.map(textsDTO, Texts.class);
        Texts updatedTexts = textsRepository.save(texts);
        return modelMapper.map(updatedTexts, TextsDTO.class);
    }

    @Override
    public List<TextsDTO> findByUsername(String query) {
        List<Texts> textsList = textsRepository.findAllByUsername(query);

        if (textsList.isEmpty()) {
            throw new NotFoundException("Элементы не найдены");
        }

        return textsList.stream().map(texts -> modelMapper.map(texts, TextsDTO.class)).collect(Collectors.toList());
    }
}
