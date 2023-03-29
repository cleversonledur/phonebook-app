import "./App.css";
import { Router, Route, Switch } from "react-router-dom";
import AppContext from "./context/AppContext";
import { styled, createTheme, ThemeProvider } from "@mui/material/styles";

import { createBrowserHistory } from "history";
import PhoneBook from "./containers/Phonebook";
const history = createBrowserHistory();

export const themeOptions: ThemeOptions = {};
const mdTheme = createTheme(themeOptions);

function App() {
  return (
    <ThemeProvider theme={mdTheme}>
      <AppContext.Provider value={{}}>
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
