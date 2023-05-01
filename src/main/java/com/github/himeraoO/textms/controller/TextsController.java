package com.github.himeraoO.textms.controller;

import com.github.himeraoO.textms.DTO.TextsDTO;
import com.github.himeraoO.textms.exception.NotFoundException;
import com.github.himeraoO.textms.handle_exception.NotFoundError;
import com.github.himeraoO.textms.service.TextsService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/")
public class TextsController {

    private final TextsService textsService;

    @Autowired
    public TextsController(TextsService textsService) {
        this.textsService = textsService;
    }

    @GetMapping(value = "/text")
    public ResponseEntity<List<TextsDTO>> getAllTexts() {
        ResponseEntity<List<TextsDTO>> responseEntity = new ResponseEntity<List<TextsDTO>>(textsService.findAll(), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping(value = "/text")
    public ResponseEntity<TextsDTO> saveTexts(@RequestBody TextsDTO textsDTO) {
        ResponseEntity<TextsDTO> responseEntity = new ResponseEntity<>(textsService.save(textsDTO), HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(value = "/text/{id}")
    public ResponseEntity<TextsDTO> getTexts(@PathVariable Long id) {
        ResponseEntity<TextsDTO> responseEntity = new ResponseEntity<>(textsService.findTexts(id), HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping(value = "/text/{id}")
    public ResponseEntity<TextsDTO> updateTexts(@PathVariable Long id, @RequestBody TextsDTO textsDTO) {
        ResponseEntity<TextsDTO> responseEntity = new ResponseEntity<>(textsService.update(id, textsDTO), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping(value = "/text/{id}")
    public ResponseEntity<TextsDTO> deleteTexts(@PathVariable Long id) {
//        ResponseEntity<TextsDTO> responseEntity = new ResponseEntity<>(textsService.delete(id), HttpStatus.OK);
//        return responseEntity;
        return null;
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<NotFoundError> handleException(NotFoundException exception){
        NotFoundError notFoundError = new NotFoundError();
        notFoundError.setError(exception.getMessage());
        notFoundError.setStatusCode(404);

        return new ResponseEntity<>(notFoundError, HttpStatus.NOT_FOUND);
    }
}
