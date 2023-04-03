package com.phonebookapp.backend.service.impl;

import com.phonebookapp.backend.model.Contact;
import com.phonebookapp.backend.repository.ContactRepository;
import com.phonebookapp.backend.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** Contact service implementation. */
@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

  @Autowired private ContactRepository contactRepository;

  /**
   * Get all contacts.
   *
   * @param firstName first name
   * @param lastName last name
   * @param phoneNumber phone number
   * @return list of contacts
   */
  @Override
  public List<Contact> getAll(
      String firstName, String lastName, String phoneNumber, String search) {
    if (firstName == null) firstName = "";
    if (lastName == null) lastName = "";
    if (phoneNumber == null) phoneNumber = "";

    if (search != null && !search.isEmpty()) {
      return contactRepository.findContactByNameUsingAggregation(search);
    }

    if (firstName.isEmpty() && lastName.isEmpty() && phoneNumber.isEmpty()) {
      return contactRepository.findAll();
    } else {
      return contactRepository
          .findByFirstNameLikeAndLastNameLikeAndPhoneNumberLikeOrderByFirstNameAsc(
              firstName, lastName, phoneNumber);
    }
  }

  /**
   * Get contact by id.
   *
   * @param id contact id
   * @return contact
   */
  @Override
  public Contact getById(String id) {

    validate(id == null || id.isEmpty(), "Id is required");

    var contact = contactRepository.findById(id);
    if (contact.isEmpty()) {
      log.error("Contact with id: {} does not exist", id);
      throw new IllegalArgumentException("Contact with id: " + id + " does not exist");
    }
    return contact.get();
  }

  /**
   * Create contact.
   *
   * @param contact contact
   * @return created contact
   */
  @Override
  public Contact create(Contact contact) {
    validateContact(contact);
    return contactRepository.save(contact);
  }

  /**
   * Update contact.
   *
   * @param contact contact
   * @return updated contact
   */
  @Override
  public Contact update(Contact contact) {
    validateContact(contact);
    if (!contactRepository.existsById(contact.getId())) {
      log.error("Contact with id: {} does not exist", contact.getId());
      throw new IllegalArgumentException("Contact with id: " + contact.getId() + " does not exist");
    }
    return contactRepository.save(contact);
  }

  /**
   * Delete contact.
   *
   * @param id contact id
   */
  @Override
  public void delete(String id) {

    validate(id == null || id.isEmpty(), "Id is required");

    if (!contactRepository.existsById(id)) {
      log.error("Contact with id: {} does not exist", id);
      throw new IllegalArgumentException("Contact with id: " + id + " does not exist");
    }
    contactRepository.deleteById(id);
  }

  /**
   * Validate condition.
   *
   * @param validationCondition validation condition
   * @param validationDescription validation description
   */
  private static void validate(boolean validationCondition, String validationDescription) {
    if (validationCondition) {
      log.error(validationDescription);
      throw new IllegalArgumentException(validationDescription);
    }
  }

  /**
   * Validate contact.
   *
   * @param contact contact
   */
  private void validateContact(Contact contact) {
    validate(contact == null, "Contact is required");
    validate(contact.getFirstName() == null, "First name is required");
    validate(contact.getLastName() == null, "Last name is required");
    validate(contact.getPhoneNumber() == null, "Phone number is required");
  }
}
