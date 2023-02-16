import React from "react";
import BoardCommentCard from "./BoardCommentCard";

import BoardSubCommentCard from "./BoardSubCommentCard";

function BoardComment(props) {
  return (
    <div>
      {props.isNestedComment === 0 && (
        <BoardCommentCard
          comment_id={props.id}
          content={props.content}
          write_date={props.writeDate}
          nickname={props.writer}
        />
      )}

      {props.isNestedComment === 1 && (
        <BoardSubCommentCard
          comment_id={props.id}
          content={props.content}
          write_date={props.writeDate}
          nickname={props.writer}
        />
      )}
    </div>
  );
}

export default BoardComment;
