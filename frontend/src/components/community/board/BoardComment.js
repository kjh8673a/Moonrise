import React from "react";
import BoardCommentCard from "./BoardCommentCard";

import BoardSubCommentCard from "./BoardSubCommentCard";

function BoardComment(props) {

  const addSubCommentConfirm = () => {
    props.addSubCommentConfirm();
  }

  const deleteCommentConfirm = () => {
    props.deleteCommentConfirm();
  }

  const editCommentConfirm = () => {
    props.editCommentConfirm();
  }

  return (
    <div>
      {props.isNestedComment === 0 && (
        <BoardCommentCard
          comment_id={props.id}
          content={props.content}
          write_date={props.writeDate}
          nickname={props.writer}
          boardCommentStatus={props.boardCommentStatus}
          addSubCommentConfirm={addSubCommentConfirm}
          deleteCommentConfirm={deleteCommentConfirm}
          editCommentConfirm={editCommentConfirm}
        />
      )}

      {props.isNestedComment === 1 && (
        <BoardSubCommentCard
          comment_id={props.id}
          content={props.content}
          write_date={props.writeDate}
          nickname={props.writer}
          boardCommentStatus={props.boardCommentStatus}
          addSubCommentConfirm={addSubCommentConfirm}
          deleteCommentConfirm={deleteCommentConfirm}
          editCommentConfirm={editCommentConfirm}
        />
      )}
    </div>
  );
}

export default BoardComment;
