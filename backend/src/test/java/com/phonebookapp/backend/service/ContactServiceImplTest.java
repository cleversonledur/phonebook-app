package com.phonebookapp.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.phonebookapp.backend.service.impl.ContactServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.phonebookapp.backend.model.Contact;
import com.phonebookapp.backend.repository.ContactRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {
  @Mock private ContactRepository contactRepository;

  @InjectMocks private ContactServiceImpl contactService;

  @Test
  public void getAll_shouldReturnNonNullListOfContacts_whenValidSearchParametersAreSupplied() {
    when(contactRepository.findByFirstNameLikeAndLastNameLikeAndPhoneNumberLikeOrderByFirstNameAsc(
            "John", "Doe", "1234567890"))
        .thenReturn(List.of(new Contact("1", "John", "Doe", "1234567890")));

    List<Contact> contacts = contactService.getAll("John", "Doe", "1234567890", null);

    assertNotNull(contacts);
  }

  @Test
  public void getById_shouldReturnNonNullContact_whenValidIDIsSupplied() {

    when(contactRepository.findById("1"))
        .thenReturn(java.util.Optional.of(new Contact("1", "John", "Doe", "1234567890")));

    Contact contact = contactService.getById("1");

    assertNotNull(contact);
  }

  @Test
  public void create_shouldCreateAndReturnNonNullContact_whenCorrectParametersAreSupplied() {
    when(contactRepository.save(any(Contact.class)))
        .thenReturn(new Contact("1", "John", "Doe", "1234567890"));

    Contact contact = new Contact();
    contact.setFirstName("John");
    contact.setLastName("Doe");
    contact.setPhoneNumber("1234567890");

    Contact createdContact = contactService.create(contact);

    assertNotNull(createdContact);
    assertEquals("John", createdContact.getFirstName());
    assertEquals("Doe", createdContact.getLastName());
    assertEquals("1234567890", createdContact.getPhoneNumber());
  }

  @Test
  public void update_shouldCreateAndReturnNonNullContact_whenCorrectParametersAreSupplied() {

    when(contactRepository.existsById("1")).thenReturn(true);
    when(contactRepository.save(any(Contact.class)))
        .thenReturn(new Contact("1", "John", "Doe", "1234567890"));

    Contact contact = new Contact();
    contact.setId("1");
    contact.setFirstName("John");
    contact.setLastName("Doe");
    contact.setPhoneNumber("1234567890");

    Contact updatedContact = contactService.update(contact);

    assertNotNull(updatedContact);
    assertEquals("John", updatedContact.getFirstName());
    assertEquals("Doe", updatedContact.getLastName());
    assertEquals("1234567890", updatedContact.getPhoneNumber());
  }

  @Test
  public void delete_shouldDeleteAndReturn_whenCorrectIdIsSupplied() {

    when(contactRepository.existsById("1")).thenReturn(true);
    contactService.delete("1");
  }
}
