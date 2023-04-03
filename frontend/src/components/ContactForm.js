import { Button, Grid, TextField, Typography } from "@mui/material";
import React, { useContext } from "react";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import PhonebookContext from "../context/PhonebookContext";
import { createContact, updateContact } from "../service/contacts";
import InputMask from "react-input-mask";
import AppContext from "../context/AppContext";

export default function ContactForm(props) {
  const { handleBackClick } = props;

  const { selectedContact } = useContext(PhonebookContext);

  const [firstName, setFirstName] = React.useState("");
  const [lastName, setLastName] = React.useState("");
  const [phoneNumber, setPhoneNumber] = React.useState("");
  const { setErrorMessage, setSuccessMessage } = useContext(AppContext);

  React.useEffect(() => {
    if (selectedContact) {
      setFirstName(selectedContact.firstName);
      setLastName(selectedContact.lastName);
      setPhoneNumber(selectedContact.phoneNumber);
    }
  }, [selectedContact]);

  const handleSaveClick = () => {
    // call service to save contact
    // then update the contacts list
    // then go back to the list
    if (!validateContact()) {
      return;
    }
    if (selectedContact) {
      selectedContact.firstName = firstName;
      selectedContact.lastName = lastName;
      selectedContact.phoneNumber = phoneNumber;

      updateContact(selectedContact)
        .then((response) => {
          setSuccessMessage("Contact updated successfully");
          console.log(response);
          handleBackClick();
        })
        .catch((error) => {
          setErrorMessage("Error updating contact: " + error.message);
          console.log(error);
        });
    } else {
      createContact({
        firstName: firstName,
        lastName: lastName,
        phoneNumber: phoneNumber,
      })
        .then((response) => {
          console.log(response);
          setSuccessMessage("Contact created successfully");
          handleBackClick();
        })
        .catch((error) => {
          setErrorMessage("Error creating contact: " + error.message);
          console.log(error);
        });
    }
  };

  const validateContact = () => {
    if (!firstName) {
      setErrorMessage("First name is required");
      return false;
    }
    if (!lastName) {
      setErrorMessage("Last name is required");
      return false;
    }
    if (!phoneNumber) {
      setErrorMessage("Phone number is required");
      return false;
    }
    return true;
  };

  return (
    <div>
      <Grid
        item
        container
        justifyContent="center"
        alignItems="center"
        sx={{ paddingBottom: "10px" }}
      >
        <Grid item xs={6}>
          <Button onClick={handleBackClick}>
            <ArrowBackIosIcon />
          </Button>
        </Grid>
        <Grid item xs={6} style={{ textAlign: "right" }}></Grid>
        <Typography variant="h5">New Contact</Typography>
      </Grid>
      <Grid
        item
        container
        justifyContent="center"
        alignItems="center"
        spacing={2}
      >
        <Grid item xs={12}>
          <TextField
            fullWidth
            required
            value={firstName}
            onChange={(event) => setFirstName(event.target.value)}
            id="outlined-basic"
            label="First Name"
            variant="outlined"
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            fullWidth
            required
            value={lastName}
            onChange={(event) => setLastName(event.target.value)}
            id="outlined-basic"
            label="Last Name"
            variant="outlined"
          />
        </Grid>
        <Grid item xs={12}>
          <InputMask
            mask="999-999-9999"
            required
            value={phoneNumber}
            onChange={(event) => setPhoneNumber(event.target.value)}
          >
            {() => <TextField label="Phone Number" fullWidth />}
          </InputMask>
        </Grid>
      </Grid>
      <Grid container spacing={2} sx={{ paddingTop: "20px" }}>
        <Grid item xs={12}>
          <Button variant="contained" color="primary" onClick={handleSaveClick}>
            {selectedContact ? "Update Contact" : "Create Contact"}
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}
