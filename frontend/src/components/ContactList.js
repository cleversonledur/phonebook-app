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
import DeleteIcon from "@mui/icons-material/Delete";

export default function ContactList(props) {
  const { handleCreateContactClick } = props;
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
          />
        </Grid>
      </Grid>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          {/* <--! list of contacts --> */}
          <List style={{ width: "100%" }}>
            <ListItem
              style={{
                width: "100%",
                border: "1px solid #ccc",
                backgroundColor: "#fff",
              }}
            >
              <Grid item xs={6}>
                aaa
              </Grid>
              <Grid item xs={6} style={{ textAlign: "right" }}>
                <IconButton aria-label="delete" size="large">
                  <DeleteIcon fontSize="inherit" />
                </IconButton>
              </Grid>
            </ListItem>
            <ListItem disablePadding></ListItem>
          </List>
        </Grid>
      </Grid>
    </div>
  );
}
