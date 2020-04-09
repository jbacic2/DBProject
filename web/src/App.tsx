import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import { ThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import { Home } from "./home";
import {
  makeStyles,
  AppBar,
  Toolbar,
  IconButton,
  Typography,
} from "@material-ui/core";
import {
  Search as SearchIcon,
  Menu as MenuIcon,
  More as MoreIcon,
} from "@material-ui/icons";

const theme = createMuiTheme({
  palette: {
    primary: {
      main: "#2AA17F",
    },
    secondary: {
      main: "#2AA17F",
    },
  },
});

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  toolbar: {
    minHeight: 128,
    alignItems: "flex-start",
    paddingTop: theme.spacing(1),
    paddingBottom: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
    alignSelf: "flex-end",
  },
}));

export const App = () => {
  const classes = useStyles();

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <div>
          <div className={classes.root}>
            <AppBar position="static">
              <Toolbar className={classes.toolbar}>
                <IconButton
                  edge="start"
                  className={classes.menuButton}
                  color="inherit"
                  aria-label="open drawer"
                >
                  <MenuIcon />
                </IconButton>
                <Typography className={classes.title} variant="h5" noWrap>
                  Look inna Book
                </Typography>
                <IconButton aria-label="search" color="inherit">
                  <SearchIcon />
                </IconButton>
                <IconButton
                  aria-label="display more actions"
                  edge="end"
                  color="inherit"
                >
                  <MoreIcon />
                </IconButton>
              </Toolbar>
            </AppBar>
          </div>
          <nav>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/test">Test</Link>
              </li>
              <li>
                <Link to="/other">Other</Link>
              </li>
            </ul>
          </nav>
          <Switch>
            <Route path="/test">Test!</Route>
            <Route path="/other">Other!</Route>
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </div>
      </Router>
    </ThemeProvider>
  );
};
