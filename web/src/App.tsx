import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

export const App = () => (
  <Router>
    <div>
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
        <Route path="/">Home</Route>
      </Switch>
    </div>
  </Router>
);
