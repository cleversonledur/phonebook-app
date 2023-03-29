import {
  Button,
  Grid,
  IconButton,
  Input,
  List,
  ListItem,
  TextField,
  Typography,
} from "@mui/material";
import React from "react";
import ContactPhoneIcon from "@mui/icons-material/ContactPhone";
import ContactList from "../components/ContactList";
import ContactNew from "../components/ContactNew";

export default function PhoneBook() {
  const [showList, setShowList] = React.useState(true);

  const handleBackClick = () => {
    setShowList(true);
  };

  const handleCreateContactClick = () => {
    setShowList(false);
  };

  return (
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
        <ContactNew handleBackClick={handleBackClick} />
      )}
    </Grid>
  );
}
