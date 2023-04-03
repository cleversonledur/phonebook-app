package com.phonebookapp.backend.controller;

import com.phonebookapp.backend.model.Contact;
import com.phonebookapp.backend.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/** Contact controller implementation. */
@Slf4j
@Service
public class ContactControllerImpl implements ContactController {

  /** Contact service. */
  private ContactService contactService;

  /**
   * Constructor.
   *
   * @param contactService contact service
   */
  @Autowired
  ContactControllerImpl(ContactService contactService) {
    this.contactService = contactService;
  }

  /**
   * Get all contacts.
   *
   * @param firstName first name
   * @param lastName last name
   * @param phoneNumber phone number
   * @param search search
   * @return list of contacts
   */
  @Override
  public ResponseEntity<?> getAll(
      String firstName, String lastName, String phoneNumber, String search) {
    log.info("Getting all contacts");
    try {
      List<Contact> contactList = contactService.getAll(firstName, lastName, phoneNumber, search);
      return contactList != null && !contactList.isEmpty()
          ? ResponseEntity.ok(contactList)
          : ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error processing request");
    }
  }

  /**
   * Get contact by id.
   *
   * @param id contact id
   * @return contact
   */
  @Override
  public ResponseEntity<?> getById(String id) {
    try {
      log.info("Getting contact with id: {}", id);
      Contact contact = contactService.getById(id);
      return ResponseEntity.ok(contact);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contact found with id: " + id);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error processing request");
    }
  }

  /**
   * Create contact.
   *
   * @param contact contact
   * @return created contact
   */
  @Override
  public ResponseEntity<?> create(Contact contact) {
    try {
      log.info("Creating contact: {}", contact);
      Contact createdContact = contactService.create(contact);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error processing request");
    }
  }

  /**
   * Update contact.
   *
   * @param contact contact
   * @param id contact id
   * @return updated contact
   */
  @Override
  public ResponseEntity<?> update(Contact contact, String id) {
    try {
      log.info("Updating contact: {}", contact);
      Contact createdContact = contactService.update(contact);
      return ResponseEntity.status(HttpStatus.OK).body(createdContact);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error processing request");
    }
  }

  /**
   * Delete contact.
   *
   * @param id contact id
   * @return response entity
   */
  @Override
  public ResponseEntity delete(String id) {
    try {
      log.info("Deleting contact with id: {}", id);
      contactService.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error processing request");
    }
  }
}
