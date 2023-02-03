import React from "react";
import BoardCommentCard from "./BoardCommentCard";

import BoardSubCommentCard from "./BoardSubCommentCard";

const DUMMY_DATA = [
  {
    board_id: 1,
    comment_id: 1,
    content: "오리지널 댓글1",
    write_date: "2023.01.17 09:54",
    group_id: 1,
    nickname: "홍길동1",
    nested: 0,
  },
  {
    board_id: 1,
    comment_id: 2,
    content: "대댓글1->2",
    write_date: "2023.01.17 09:54",
    group_id: 1,
    nickname: "홍길동2",
    nested: 1,
  },
  {
    board_id: 1,
    comment_id: 3,
    content: "대댓글1->3",
    write_date: "2023.01.17 09:54",
    group_id: 1,
    nickname: "홍길동3",
    nested: 1,
  },
  {
    board_id: 1,
    comment_id: 4,
    content: "오리지널 댓글4",
    write_date: "2023.01.17 09:54",
    group_id: 4,
    nickname: "홍길동4",
    nested: 0,
  },
  {
    board_id: 1,
    comment_id: 5,
    content: "오리지널 댓글5",
    write_date: "2023.01.17 09:54",
    group_id: 5,
    nickname: "홍길동5",
    nested: 0,
  },
  {
    board_id: 1,
    comment_id: 6,
    content: "대댓글5->6",
    write_date: "2023.01.17 09:54",
    group_id: 5,
    nickname: "홍길동6",
    nested: 1,
  },
  {
    board_id: 1,
    comment_id: 7,
    content: "대댓글5->7",
    write_date: "2023.01.17 09:54",
    group_id: 5,
    nickname: "홍길동7",
    nested: 1,
  },
];

function BoardComment(props) {
  const comment = DUMMY_DATA.filter((data) => data.board_id === props.board_id);

  return (
    <div>
      {comment.map((comment) => (
        <>
          {comment.nested === 0 && (
            <BoardCommentCard
              comment_id={comment.comment_id}
              content={comment.content}
              write_date={comment.write_date}
              nickname={comment.nickname}
            />
          )}

          {comment.nested === 1 && (
            <BoardSubCommentCard
              comment_id={comment.comment_id}
              content={comment.content}
              write_date={comment.write_date}
              nickname={comment.nickname}
            />
          )}
        </>
      ))}
    </div>
  );
}

export default BoardComment;
