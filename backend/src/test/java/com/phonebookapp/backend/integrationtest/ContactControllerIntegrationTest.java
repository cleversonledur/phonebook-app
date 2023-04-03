package com.phonebookapp.backend.integrationtest;

import com.phonebookapp.backend.BackendApplication;
import com.phonebookapp.backend.controller.ContactControllerImpl;
import com.phonebookapp.backend.model.Contact;
import com.phonebookapp.backend.repository.ContactRepository;
import com.phonebookapp.backend.service.ContactService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(
    classes = BackendApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerIntegrationTest {

  @Autowired private ContactRepository contactRepository;

  @Autowired private ContactService contactService;

  @Autowired private ContactControllerImpl contactController;

  @Container static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Autowired private MongoTemplate mongoTemplate;

  private Contact contact;

  @BeforeEach
  public void before() {
    contactRepository.deleteAll();

    contact = new Contact();
    contact.setFirstName("John");
    contact.setLastName("Doe");
    contact.setPhoneNumber("123-456-7890");
  }

  @Test
  public void create_shouldCreateAndReturnNonNullContact_whenCorrectParametersAreSupplied() {
    contactController.create(contact);

    assertThat(contactRepository.findAll()).hasSize(1).contains(contact);
  }

  @Test
  public void getAll_shouldReturnNonNullListOfContacts_whenValidSearchParametersAreSupplied() {
    contactRepository.insert(contact);

    ResponseEntity<?> response = contactController.getAll("John", "Doe", "123-456-7890", null);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEqualTo(contactRepository.findAll());
  }

  @Test
  public void getById_shouldReturnNull_whenNonExistentIdIsSupplied() {
    ResponseEntity<?> result = contactController.getById("123");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(result.getBody()).isEqualTo("No contact found with id: 123");
  }

  @Test
  public void getById_shouldGetContactById_whenExistentIdIsSupplied() {
    contactRepository.insert(contact);

    Contact result = contactService.getById(contact.getId());

    assertThat(result).isEqualTo(contact);
  }

  @Test
  public void delete_shouldDeleteContact_whenExistentIdIsSupplied() {
    contactRepository.insert(contact);

    contactController.delete(contact.getId());

    assertThat(contactRepository.findAll()).isEmpty();
  }

  @Test
  public void update_shouldUpdateContact_whenExistentIdIsSupplied() {
    contactRepository.insert(contact);
    Contact updatedContact = contact;
    updatedContact.setPhoneNumber("098-765-4321");

    contactController.update(updatedContact, contact.getId());

    assertThat(contactRepository.findById(contact.getId()).get().getPhoneNumber())
        .isEqualTo("098-765-4321");
  }

  @Test
  public void update_shouldReturnNull_whenNonExistentIdIsSupplied() {
    Contact updatedContact = contact;
    updatedContact.setPhoneNumber("098-765-4321");

    ResponseEntity<?> result = contactController.update(updatedContact, "123");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(result.getBody()).isEqualTo("The given id must not be null");
  }

  @Test
  public void getAll_shouldReturnEmptyList_whenNoSearchParametersAreSupplied() {
    ResponseEntity<?> response = contactController.getAll(null, null, null, null);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEqualTo(List.of());
  }

  @Test
  public void delete_shouldReturnNull_whenNonExistentIdIsSupplied() {
    ResponseEntity<?> result = contactController.delete("123");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(result.getBody()).isEqualTo("Contact with id: 123 does not exist");
  }
}
