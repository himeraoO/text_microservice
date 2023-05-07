package com.github.himeraoO.textms.service.impl;

import com.github.himeraoO.textms.DTO.TextsDTO;
import com.github.himeraoO.textms.exception.ConflictException;
import com.github.himeraoO.textms.exception.NotFoundException;
import com.github.himeraoO.textms.model.Texts;
import com.github.himeraoO.textms.repository.TextsRepository;
import com.github.himeraoO.textms.service.TextsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
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

        log.info("Texts с id = {} получен", texts.getId());

        return modelMapper.map(texts, TextsDTO.class);
    }

    @Override
    public List<TextsDTO> findAll() {
        List<Texts> textsList = textsRepository.findAll();

        if (textsList.isEmpty()) {
            throw new NotFoundException("Элементы не найдены");
        }

        log.info("Получен список всех Texts");

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

        log.info("Получен постраничный список всех Texts");

        return dtoPage;
    }

    @Override
    @Transactional(readOnly = false)
    public TextsDTO update(long id, TextsDTO textsDTO) {
        Texts textsFromBd = textsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет элементов с таким id: " + id));

        textsFromBd.setUsername(textsDTO.getUsername());
        textsFromBd.setTitle(textsDTO.getTitle());
        textsFromBd.setDescription(textsDTO.getDescription());
        textsFromBd.setDataText(textsDTO.getDataText());
        Texts updatedTexts = textsRepository.save(textsFromBd);

        log.info("Texts с id = {} обновлен", updatedTexts.getId());

        return modelMapper.map(updatedTexts, TextsDTO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id) {
        Texts texts = textsRepository.findById(id).orElseThrow(() -> new NotFoundException("Нет элементов с таким id: " + id));
        textsRepository.delete(texts);

        log.info("Texts с id = {} удален", texts.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public TextsDTO save(TextsDTO textsDTO) {
        textsDTO.setId(0L);

        Optional<Texts> optionalTexts = textsRepository.findByUsernameAndTitleAndDescription(
                textsDTO.getUsername(),
                textsDTO.getTitle(),
                textsDTO.getDescription()
        );

        if(optionalTexts.isPresent()){
            throw  new ConflictException(
                    String.format(
                            "Элементы с такими значениями: username: %s, title: %s, desription: %s уже существуют.",
                            textsDTO.getUsername(),
                            textsDTO.getTitle(),
                            textsDTO.getDescription()));
        }

        Texts texts = modelMapper.map(textsDTO, Texts.class);
        Texts savedTexts = textsRepository.save(texts);

        log.info("Texts с id = {} создан", savedTexts.getId());

        return modelMapper.map(savedTexts, TextsDTO.class);
    }

    @Override
    public List<TextsDTO> findByUsername(String query) {
        List<Texts> textsList = textsRepository.findAllByUsername(query);

        if (textsList.isEmpty()) {
            throw new NotFoundException("Элементы не найдены");
        }

        log.info("Получен список всех Texts для username = {}", query);

        return textsList.stream().map(texts -> modelMapper.map(texts, TextsDTO.class)).collect(Collectors.toList());
    }
}
