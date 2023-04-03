package com.phonebookapp.backend.repository;

import com.phonebookapp.backend.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Contact repository. */
@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {

  /**
   * Find all contacts by first name, last name and phone number.
   *
   * @param firstName first name
   * @param lastName last name
   * @param phoneNumber phone number
   * @return list of contacts
   */
  List<Contact> findByFirstNameLikeAndLastNameLikeAndPhoneNumberLikeOrderByFirstNameAsc(
      String firstName, String lastName, String phoneNumber);

  @Query(
      value =
          "{ $expr: {  $and: [ { $eq: [ { $size: { $split: [{ $concat: [ \"$firstName\", \" \", \"$lastName\" ] }, \" \" ] } }, 2 ] }, { $or: [ { $eq: [ { $substr: [ { $concat: [ \"$firstName\", \" \", \"$lastName\" ] }, 0, { $strLenCP: ?0 } ] }, ?0 ] }, { $eq: [ { $substr: [ { $concat: [ \"$lastName\", \" \", \"$firstName\" ] }, 0, { $strLenCP: ?0 } ] }, ?0 ] } ] } ] } }")
  List<Contact> findContactByNameUsingAggregation(String search);
}
