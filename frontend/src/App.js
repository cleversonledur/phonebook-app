import "./App.css";
import React, { useState, useEffect } from "react";

import { Router, Route, Switch } from "react-router-dom";
import AppContext from "./context/AppContext";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Alert from "@mui/material/Alert";
import { createBrowserHistory } from "history";
import PhoneBook from "./containers/Phonebook";
const history = createBrowserHistory();

export const themeOptions: ThemeOptions = {};
const mdTheme = createTheme(themeOptions);
function App() {
  const [showErrorAlert, setShowErrorAlert] = useState(false);
  const [showSuccessAlert, setShowSuccessAlert] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    if (successMessage === "") return;
    setShowSuccessAlert(true);
    const timer = setTimeout(() => {
      setShowSuccessAlert(false);
    }, 3000);
    return () => clearTimeout(timer);
  }, [successMessage]);

  useEffect(() => {
    if (errorMessage === "") return;
    setShowErrorAlert(true);
    const timer = setTimeout(() => {
      setShowErrorAlert(false);
    }, 3000);
    return () => clearTimeout(timer);
  }, [errorMessage]);

  return (
    <ThemeProvider theme={mdTheme}>
      {showErrorAlert && <Alert severity="error">{errorMessage}</Alert>}
      {showSuccessAlert && <Alert severity="success">{successMessage}</Alert>}
      <AppContext.Provider
        value={{
          showList: true,
          showErrorAlert: showErrorAlert,
          setShowErrorAlert: setShowErrorAlert,
          showSuccessAlert: showSuccessAlert,
          setShowSuccessAlert: setShowSuccessAlert,
          errorMessage: errorMessage,
          setErrorMessage: setErrorMessage,
          successMessage: successMessage,
          setSuccessMessage: setSuccessMessage,
        }}
      >
        <Router history={history}>
          <Switch>
            <Route path="/" exact component={PhoneBook} />
          </Switch>
        </Router>
      </AppContext.Provider>
    </ThemeProvider>
  );
}

export default App;
