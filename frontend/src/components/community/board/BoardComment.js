import React from "react";
import BoardCommentCard from "./BoardCommentCard";

import BoardSubCommentCard from "./BoardSubCommentCard";

function BoardComment(props) {

  const addSubCommentConfirm = () => {
    props.addSubCommentConfirm();
  }

  return (
    <div>
      {props.isNestedComment === 0 && (
        <BoardCommentCard
          comment_id={props.id}
          content={props.content}
          write_date={props.writeDate}
          nickname={props.writer}
          addSubCommentConfirm={addSubCommentConfirm}
        />
      )}

      {props.isNestedComment === 1 && (
        <BoardSubCommentCard
          comment_id={props.id}
          content={props.content}
          write_date={props.writeDate}
          nickname={props.writer}
          addSubCommentConfirm={addSubCommentConfirm}
        />
      )}
    </div>
  );
}

export default BoardComment;
