import React from "react";
import { Card, CardContent, Typography } from "@material-ui/core";

export interface BookSummary {
  title: string;
  author: string;
  isbn: string;
}

export const BookCard = (props: BookSummary) => {
  return (
    <Card>
      <CardContent>
        <Typography variant="h6">{props.title}</Typography>
        <Typography variant="subtitle1">{props.author}</Typography>
        <Typography variant="overline">{props.isbn}</Typography>
      </CardContent>
    </Card>
  );
};
