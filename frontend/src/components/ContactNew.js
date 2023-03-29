import {
  Button,
  Grid,
  IconButton,
  List,
  ListItem,
  TextField,
  Typography,
} from "@mui/material";
import React from "react";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
export default function ContactNew(props) {
  const { handleBackClick } = props;

  return (
    <div>
      {/* form to create a contact with first name, last name, and phone number */}

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
            id="outlined-basic"
            label="First Name"
            variant="outlined"
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            fullWidth
            id="outlined-basic"
            label="Last Name"
            variant="outlined"
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            fullWidth
            id="outlined-basic"
            label="Phone Number"
            variant="outlined"
          />
        </Grid>
      </Grid>
      <Grid container spacing={2} sx={{ paddingTop: "20px" }}>
        <Grid item xs={12}>
          <Button variant="contained" color="primary">
            Save
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}
