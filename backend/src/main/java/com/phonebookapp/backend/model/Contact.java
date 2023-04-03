package com.phonebookapp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

/** Contact model. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "contacts")
public class Contact {

  /** Contact id. */
  private String id;

  /** First name. */
  @NotNull
  @Size(min = 2, max = 50)
  private String firstName;

  /** Last name. */
  @NotNull
  @Size(min = 2, max = 50)
  private String lastName;

  /** Phone number. */
  @NotNull
  @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$")
  private String phoneNumber;
}
