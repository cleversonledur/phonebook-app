import {
  Button,
  Grid,
  IconButton,
  List,
  ListItem,
  TextField,
  Typography,
} from "@mui/material";
import React, { useContext } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import PhonebookContext from "../context/PhonebookContext";
import ConfirmationDialog from "./ConfirmationDialog";
import PhoneIcon from "@mui/icons-material/Phone";
import { deleteContact } from "../service/contacts";
import AppContext from "../context/AppContext";

export default function ContactList(props) {
  const { setSelectedContact, contacts, loadContacts } =
    useContext(PhonebookContext);

  const { setErrorMessage, setSuccessMessage } = useContext(AppContext);

  const { handleCreateContactClick } = props;

  const [openDeleteDialog, setOpenDeleteDialog] = React.useState(false);
  const [contactToDelete, setContactToDelete] = React.useState(null);
  const [searchText, setSearchText] = React.useState("");

  React.useEffect(() => {
    loadContacts(searchText);
  }, [searchText]);

  const handleContactClick = (contact) => {
    setSelectedContact(contact);
  };

  const handleDeleteClick = () => {
    console.log("delete contact", contactToDelete);
    // call service to delete contact
    // then update the contacts list
    deleteContact(contactToDelete.id)
      .then((response) => {
        setSuccessMessage("Contact deleted successfully");
        setContactToDelete(null);
        setOpenDeleteDialog(false);
        loadContacts(searchText);
      })
      .catch((error) => {
        setErrorMessage("Error deleting contact: " + error.message);
        console.log(error);
      });
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
          <Typography variant="h5">Contacts</Typography>
        </Grid>
        <Grid item xs={6} style={{ textAlign: "right" }}>
          <Button
            variant="contained"
            color="primary"
            onClick={handleCreateContactClick}
          >
            + Add Contact
          </Button>
        </Grid>
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
            id="outlined-basic"
            label="Search"
            variant="outlined"
            value={searchText}
            onChange={(event) => setSearchText(event.target.value)}
          />
        </Grid>
      </Grid>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          {contacts ? (
            <List style={{ width: "100%" }}>
              {contacts.map((contact) => (
                <ListItem
                  onClick={() => handleContactClick(contact)}
                  style={{
                    width: "100%",
                    border: "1px solid #ccc",
                    backgroundColor: "#fff",
                  }}
                >
                  <Grid item xs={6}>
                    <Typography variant="h6">
                      {contact.firstName} {contact.lastName}
                    </Typography>
                    <Typography
                      variant="body2"
                      sx={{
                        color: "#999",
                        fontWeight: "bold",
                      }}
                    >
                      <PhoneIcon fontSize="inherit" />
                      {contact.phoneNumber}
                    </Typography>
                  </Grid>
                  <Grid item xs={6} style={{ textAlign: "right" }}>
                    <IconButton
                      aria-label="delete"
                      size="large"
                      onClick={(e) => {
                        e.stopPropagation();
                        setOpenDeleteDialog(true);
                        setContactToDelete(contact);
                      }}
                    >
                      <DeleteIcon fontSize="inherit" />
                    </IconButton>
                  </Grid>
                </ListItem>
              ))}
            </List>
          ) : (
            <List style={{ width: "100%" }}>
              <ListItem
                style={{
                  width: "100%",
                  border: "1px dashed #ccc",
                  backgroundColor: "#fff",
                  color: "#999",
                  alignItems: "center",
                  justifyContent: "center",
                }}
              >
                <div>No contacts found</div>
              </ListItem>
            </List>
          )}
        </Grid>
      </Grid>
      <ConfirmationDialog
        open={openDeleteDialog}
        setOpen={setOpenDeleteDialog}
        title="Delete Contact"
        message="Are you sure you want to delete this contact?"
        onConfirm={handleDeleteClick}
        actionName="Delete"
      />
    </div>
  );
}
