package com.phonebookapp.backend.controller;

import com.phonebookapp.backend.model.Contact;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@Api(value = "Contact Management System")
public interface ContactController {

  @ApiOperation(value = "View a list of available contacts", response = List.class)
  @GetMapping
  ResponseEntity<?> getAll(
      @RequestParam(required = false) String firstName,
      @RequestParam(required = false) String lastName,
      @RequestParam(required = false) String phoneNumber,
      @RequestParam(required = false) String search);

  @ApiOperation(value = "Get a contact by Id")
  @GetMapping("/{id}")
  ResponseEntity<?> getById(
      @ApiParam(value = "Contact id from which contact object will retrieve", required = true)
          @PathVariable
          String id);

  @ApiOperation(value = "Add a contact")
  @PostMapping
  ResponseEntity<?> create(@RequestBody Contact contact);

  @ApiOperation(value = "Update a contact")
  @PutMapping("/{id}")
  ResponseEntity<?> update(@RequestBody Contact contact, @PathVariable String id);

  @ApiOperation(value = "Delete a contact")
  @DeleteMapping("/{id}")
  ResponseEntity delete(@PathVariable String id);
}
