package com.phonebookapp.backend.controller;

import com.phonebookapp.backend.model.Contact;
import com.phonebookapp.backend.repository.ContactRepository;
import com.phonebookapp.backend.service.ContactService;
import com.phonebookapp.backend.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactControllerImplTest {
  @Mock private ContactService contactRepository;

  @InjectMocks private ContactControllerImpl contactService;

  @Test
  public void getAll_shouldReturnAListOfContacts_whenValidSearchTextIsSupplied() {
    // given
    String firstName = "John";
    String lastName = "Doe";
    String phoneNumber = "1234567";
    String search = "John";
    Contact contact =
        Contact.builder().firstName(firstName).lastName(lastName).phoneNumber(phoneNumber).build();
    List<Contact> contacts = Arrays.asList(contact);
    when(contactRepository.getAll(firstName, lastName, phoneNumber, search)).thenReturn(contacts);

    // when
    ResponseEntity<?> responseEntity =
        contactService.getAll(firstName, lastName, phoneNumber, search);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertTrue(responseEntity.getBody() instanceof List);
    List<Contact> result = (List<Contact>) responseEntity.getBody();
    assertEquals(1, result.size());
    assertEquals(contact, result.get(0));
  }

  @Test
  public void getAll_shouldReturnNoContentStatus_whenNoContactsFound() {
    // given
    String firstName = "";
    String lastName = "";
    String phoneNumber = "";
    String search = "";
    List<Contact> contacts = List.of();
    when(contactRepository.getAll(firstName, lastName, phoneNumber, search)).thenReturn(contacts);

    // when
    ResponseEntity<?> responseEntity =
        contactService.getAll(firstName, lastName, phoneNumber, search);

    // then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    assertEquals(List.of(), responseEntity.getBody());
  }

  @Test
  public void getAll_shouldReturnInternalServerErrorStatus_whenExceptionIsThrown() {
    // given
    String firstName = "";
    String lastName = "";
    String phoneNumber = "";
    String search = "";
    when(contactRepository.getAll(firstName, lastName, phoneNumber, search))
        .thenThrow(RuntimeException.class);

    // when
    ResponseEntity<?> responseEntity =
        contactService.getAll(firstName, lastName, phoneNumber, search);

    // then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    assertEquals("Error processing request", responseEntity.getBody());
  }

  @Test
  public void getById_shouldReturnAContact_whenValidIdIsSupplied() {
    // given
    String id = "12345";
    Contact contact =
        Contact.builder().firstName("John").lastName("Doe").phoneNumber("1234567").build();
    when(contactRepository.getById(id)).thenReturn(contact);

    // when
    ResponseEntity<?> responseEntity = contactService.getById(id);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertTrue(responseEntity.getBody() instanceof Contact);
    Contact result = (Contact) responseEntity.getBody();
    assertEquals(contact, result);
  }

  @Test
  public void getById_shouldReturnNotFoundStatus_whenInvalidIdIsSupplied() {
    // given
    String id = "12345";
    when(contactRepository.getById(id)).thenThrow(IllegalArgumentException.class);

    // when
    ResponseEntity<?> responseEntity = contactService.getById(id);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("No contact found with id: 12345", responseEntity.getBody());
  }

  @Test
  public void getById_shouldReturnInternalServerErrorStatus_whenExceptionIsThrown() {
    // given
    String id = "12345";
    when(contactRepository.getById(id)).thenThrow(RuntimeException.class);

    // when
    ResponseEntity<?> responseEntity = contactService.getById(id);

    // then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }

  @Test
  public void create_shouldReturnCreatedStatus_whenValidContactIsSupplied() {
    // given
    Contact contact =
        Contact.builder().firstName("John").lastName("Doe").phoneNumber("1234567").build();
    when(contactRepository.create(contact)).thenReturn(contact);

    // when
    ResponseEntity<?> responseEntity = contactService.create(contact);

    // then
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertTrue(responseEntity.getBody() instanceof Contact);
    Contact result = (Contact) responseEntity.getBody();
    assertEquals(contact, result);
  }

  @Test
  public void create_shouldReturnBadRequestStatus_whenInvalidContactIsSupplied() {
    // given
    Contact contact =
        Contact.builder().firstName("John").lastName("Doe").phoneNumber("1234567").build();
    when(contactRepository.create(contact)).thenThrow(IllegalArgumentException.class);

    // when
    ResponseEntity<?> responseEntity = contactService.create(contact);

    // then
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  public void create_shouldReturnInternalServerErrorStatus_whenExceptionIsThrown() {
    // given
    Contact contact =
        Contact.builder().firstName("John").lastName("Doe").phoneNumber("1234567").build();
    when(contactRepository.create(contact)).thenThrow(RuntimeException.class);

    // when
    ResponseEntity<?> responseEntity = contactService.create(contact);

    // then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }

  @Test
  public void update_shouldReturnOkStatus_whenValidContactIsSupplied() {
    // given
    Contact contact =
        Contact.builder().firstName("John").lastName("Doe").phoneNumber("1234567").build();
    when(contactRepository.update(contact)).thenReturn(contact);

    // when
    ResponseEntity<?> responseEntity = contactService.update(contact, "12345");

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertTrue(responseEntity.getBody() instanceof Contact);
    Contact result = (Contact) responseEntity.getBody();
    assertEquals(contact, result);
  }

  @Test
  public void update_shouldReturnInternalServerErrorStatus_whenExceptionIsThrown() {
    // given
    Contact contact =
        Contact.builder().firstName("John").lastName("Doe").phoneNumber("1234567").build();
    when(contactRepository.update(contact)).thenThrow(RuntimeException.class);

    // when
    ResponseEntity<?> responseEntity = contactService.update(contact, contact.getId());

    // then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }

  @Test
  public void update_shouldReturnBadRequestStatus_whenInvalidContactIsSupplied() {
    // given
    Contact contact =
        Contact.builder().firstName("John").lastName("Doe").phoneNumber("1234567").build();
    when(contactRepository.update(contact)).thenThrow(IllegalArgumentException.class);

    // when
    ResponseEntity<?> responseEntity = contactService.update(contact, "12345");

    // then
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  public void delete_shouldReturnNoContentStatus_whenValidIdIsSupplied() {
    // given
    String id = "12345";
    doNothing().when(contactRepository).delete(id);

    // when
    ResponseEntity<?> responseEntity = contactService.delete(id);

    // then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
  }

  @Test
  public void delete_shouldReturnInternalServerErrorStatus_whenExceptionIsThrown() {
    // given
    String id = "12345";
    doThrow(RuntimeException.class).when(contactRepository).delete(id);

    // when
    ResponseEntity<?> responseEntity = contactService.delete(id);

    // then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }

  @Test
  public void delete_shouldReturnNotFoundStatus_whenInvalidIdIsSupplied() {
    // given
    String id = "12345";
    doThrow(IllegalArgumentException.class).when(contactRepository).delete(id);

    // when
    ResponseEntity<?> responseEntity = contactService.delete(id);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  public void delete_shouldReturnSuccessMessage_whenValidIdIsSupplied() {
    // given
    String id = "12345";
    doNothing().when(contactRepository).delete(id);

    // when
    ResponseEntity<?> responseEntity = contactService.delete(id);

    // then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
  }
}
