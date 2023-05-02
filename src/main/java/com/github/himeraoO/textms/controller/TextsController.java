package com.github.himeraoO.textms.controller;

import com.github.himeraoO.textms.DTO.TextsDTO;
import com.github.himeraoO.textms.exception.ConflictException;
import com.github.himeraoO.textms.exception.NotFoundException;
import com.github.himeraoO.textms.handle_exception.ErrorMessage;
import com.github.himeraoO.textms.service.TextsService;
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
@RequestMapping(value = "/")
public class TextsController {

    private final TextsService textsService;

    @Autowired
    public TextsController(TextsService textsService) {
        this.textsService = textsService;
    }

    @GetMapping(value = "/text/{id}")
    public ResponseEntity<TextsDTO> getTexts(@PathVariable Long id) {
        return new ResponseEntity<>(textsService.findTexts(id), HttpStatus.OK);
    }

    @GetMapping(value = "/text")
    public ResponseEntity<List<TextsDTO>> getAllTexts() {
        List<TextsDTO> textsDTOList = textsService.findAll();

        return new ResponseEntity<>(textsDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/text?page={page}&size={size}", params = { "page", "size" })
    public ResponseEntity<Page<TextsDTO>> getWithPaginationTexts(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<TextsDTO> textsDTOPage = textsService.findWithPagination(page, size);

        return new ResponseEntity<>(textsDTOPage, HttpStatus.OK);
    }

    @GetMapping(value = "/text?username={username}", params = {"username"})
    public ResponseEntity<List<TextsDTO>> findTextsByUsername(@PathVariable("username") String username) {
        List<TextsDTO> textsDTOList = textsService.findByUsername(username);

        return new ResponseEntity<>(textsDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/text", consumes = "application/json")
    public ResponseEntity<TextsDTO> saveTexts(@RequestBody TextsDTO textsDTO) {
        return new ResponseEntity<>(textsService.save(textsDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/text/{id}", consumes = "application/json")
    public ResponseEntity<TextsDTO> updateTexts(@PathVariable Long id, @RequestBody TextsDTO textsDTO) {
        return new ResponseEntity<>(textsService.update(id, textsDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/text/{id}")
    public ResponseEntity<Void> deleteTexts(@PathVariable Long id) {
        textsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorMessage> handleException(NotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ErrorMessage> handleException(ConflictException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 409);
        errorMessage.setError(exception.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

}
