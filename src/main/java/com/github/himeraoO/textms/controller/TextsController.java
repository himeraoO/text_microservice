package com.github.himeraoO.textms.controller;

import com.github.himeraoO.textms.DTO.TextsDTO;
import com.github.himeraoO.textms.exception.ConflictException;
import com.github.himeraoO.textms.exception.NotFoundException;
import com.github.himeraoO.textms.handle_exception.ErrorMessage;
import com.github.himeraoO.textms.service.TextsService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@Slf4j
@RequestMapping(value = "/")
public class TextsController {

    private final TextsService textsService;

    @Autowired
    public TextsController(TextsService textsService) {
        this.textsService = textsService;
    }

    @GetMapping(value = "/text/{id}")
    public ResponseEntity<TextsDTO> getTexts(@PathVariable Long id) {
        log.info("Получен запрос GET /text/{}", id);

        return new ResponseEntity<>(textsService.findTexts(id), HttpStatus.OK);
    }

    @GetMapping(value = "/text")
    public ResponseEntity<List<TextsDTO>> getAllTexts() {
        log.info("Получен запрос GET /text/");
        List<TextsDTO> textsDTOList = textsService.findAll();

        return new ResponseEntity<>(textsDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/text?page={page}&size={size}", params = { "page", "size" })
    public ResponseEntity<Page<TextsDTO>> getWithPaginationTexts(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        log.info("Получен запрос GET /text?page={}&size={}", page, size);
        Page<TextsDTO> textsDTOPage = textsService.findWithPagination(page, size);

        return new ResponseEntity<>(textsDTOPage, HttpStatus.OK);
    }

    @GetMapping(value = "/text?username={username}", params = {"username"})
    public ResponseEntity<List<TextsDTO>> findTextsByUsername(@PathVariable("username") String username) {
        log.info("Получен запрос GET /text?username={}", username);
        List<TextsDTO> textsDTOList = textsService.findByUsername(username);

        return new ResponseEntity<>(textsDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/text", consumes = "application/json")
    public ResponseEntity<TextsDTO> saveTexts(@RequestBody TextsDTO textsDTO) {
        log.info("Получен запрос POST /text/");
        return new ResponseEntity<>(textsService.save(textsDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/text/{id}", consumes = "application/json")
    public ResponseEntity<TextsDTO> updateTexts(@PathVariable Long id, @RequestBody TextsDTO textsDTO) {
        log.info("Получен запрос POST /text/{}/", id);

        return new ResponseEntity<>(textsService.update(id, textsDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/text/{id}")
    public ResponseEntity<Void> deleteTexts(@PathVariable Long id) {
        log.info("Получен запрос DELETE /text/{}", id);
        textsService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorMessage> handleException(NotFoundException exception) {
        log.info("Обработка ошибки NotFoundException {}", exception.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ErrorMessage> handleException(ConflictException exception) {
        log.info("Обработка ошибки ConflictException {}", exception.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 409);

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
