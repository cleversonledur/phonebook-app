import { Grid, Typography } from "@mui/material";
import React, { useContext } from "react";
import ContactPhoneIcon from "@mui/icons-material/ContactPhone";
import ContactList from "../components/ContactList";
import ContactForm from "../components/ContactForm";
import AppContext from "../context/AppContext";
import PhonebookContext from "../context/PhonebookContext";
import { getContacts } from "../service/contacts";

export default function PhoneBook() {
  const [showList, setShowList] = React.useState(true);

  const [contacts, setContacts] = React.useState([]);
  const [selectedContact, setSelectedContact] = React.useState(null);
  const { backendURL } = useContext(AppContext);

  const { setErrorMessage } = useContext(AppContext);

  React.useEffect(() => {
    if (!showList) return;
    loadContacts();
  }, [showList, backendURL]);

  React.useEffect(() => {
    if (selectedContact) {
      setShowList(false);
    }
  }, [selectedContact]);

  const loadContacts = (searchText) => {
    getContacts(searchText)
      .then((contacts) => {
        setContacts(contacts.data);
      })
      .catch((error) => {
        setErrorMessage("Error loading contacts: " + error.message);
        console.log(error);
      });
  };

  const handleBackClick = () => {
    setSelectedContact(null);
    setShowList(true);
  };

  const handleCreateContactClick = () => {
    setShowList(false);
  };

  return (
    <PhonebookContext.Provider
      value={{
        selectedContact: selectedContact,
        setSelectedContact: setSelectedContact,
        showList: showList,
        contacts: contacts,
        loadContacts: loadContacts,
      }}
    >
      <Grid sx={{ padding: "20px" }} spacing={2}>
        <Grid
          item
          container
          justifyContent="center"
          alignItems="center"
          direction="column"
          spacing={2}
          sx={{ padding: "10px" }}
        >
          <Grid item xs={4}></Grid>
          <Grid item xs={8}>
            <Typography variant="h4">
              <ContactPhoneIcon fontSize="large" /> Phone Book App
            </Typography>
          </Grid>
        </Grid>
        {showList ? (
          <ContactList handleCreateContactClick={handleCreateContactClick} />
        ) : (
          <ContactForm handleBackClick={handleBackClick} />
        )}
      </Grid>
    </PhonebookContext.Provider>
  );
}
