import React from "react";
import { BookCard } from "./BookCard";
import { GridList, GridListTile } from "@material-ui/core";

export const Home = () => {
  return (
    <div>
      <GridList>
        {/* Turn this into a loop */}
        <GridListTile>
          <BookCard title="Test" author="Timmy" isbn="ABCD-1234" />
        </GridListTile>
        <GridListTile>
        <BookCard title="Hello World" author="Harold" isbn="ABCD-1254" />
        </GridListTile>
      </GridList>
      
    </div>
  );
};
