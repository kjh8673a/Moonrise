import React from "react";

import BoardSubComment from "./BoardSubComment";


const DUMMY_DATA = [
  {
    board_id: 1,
    comment_id: 1,
    content: "오리지널 댓글1",
    write_date: "2023.01.17 09:54",
    group_id: 0,
    nickname: "홍길동",
  },
  {
    board_id: 1,
    comment_id: 2,
    content: "대댓글1->2",
    write_date: "2023.01.17 09:54",
    group_id: 1,
    nickname: "홍길동",
  },
  {
    board_id: 1,
    comment_id: 3,
    content: "대댓글1->3",
    write_date: "2023.01.17 09:54",
    group_id: 1,
    nickname: "홍길동",
  },
  {
    board_id: 1,
    comment_id: 4,
    content: "오리지널 댓글4",
    write_date: "2023.01.17 09:54",
    group_id: 0,
    nickname: "홍길동",
  },
  {
    board_id: 1,
    comment_id: 5,
    content: "오리지널 댓글5",
    write_date: "2023.01.17 09:54",
    group_id: 0,
    nickname: "홍길동",
  },
  {
    board_id: 1,
    comment_id: 6,
    content: "대댓글5->6",
    write_date: "2023.01.17 09:54",
    group_id: 5,
    nickname: "홍길동",
  },
  {
    board_id: 1,
    comment_id: 7,
    content: "대댓글5->7",
    write_date: "2023.01.17 09:54",
    group_id: 5,
    nickname: "홍길동",
  },
];

function BoardComment(props) {
    const origin_comment = DUMMY_DATA.filter((data) => data.board_id === props.board_id && data.group_id === 0);

  return (
    <div>
      <span>댓글</span>
        {origin_comment.map((comment) => (
            <>
                <div>
                    <span>{comment.content}</span>
                </div>
                <BoardSubComment comment_id={comment.comment_id}/>
            </>
        ))}
    </div>
  );
}

export default BoardComment;
