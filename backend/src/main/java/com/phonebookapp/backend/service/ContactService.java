package com.phonebookapp.backend.service;

import com.phonebookapp.backend.model.Contact;

import java.util.List;

/** Service for managing contacts. */
public interface ContactService {

  /**
   * Get all contacts.
   *
   * @param firstName first name
   * @param lastName last name
   * @param phoneNumber phone number
   * @return list of contacts
   */
  List<Contact> getAll(String firstName, String lastName, String phoneNumber, String search);

  /**
   * Get contact by id.
   *
   * @param id contact id
   * @return contact
   */
  Contact getById(String id);

  /**
   * Create contact.
   *
   * @param contact contact
   * @return created contact
   */
  Contact create(Contact contact);

  /**
   * Update contact.
   *
   * @param contact contact
   * @return updated contact
   */
  Contact update(Contact contact);

  /**
   * Delete contact.
   *
   * @param id contact id
   */
  void delete(String id);
}
