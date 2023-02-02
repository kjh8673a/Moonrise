import React, { useState } from "react";

import BoardSubComment from "./BoardSubComment";
import BoardSubCommetInput from "./BoardSubCommetInput";

const DUMMY_DATA = [
  {
    board_id: 1,
    comment_id: 1,
    content: "오리지널 댓글1",
    write_date: "2023.01.17 09:54",
    group_id: 0,
    nickname: "홍길동1",
  },
  {
    board_id: 1,
    comment_id: 2,
    content: "대댓글1->2",
    write_date: "2023.01.17 09:54",
    group_id: 1,
    nickname: "홍길동2",
  },
  {
    board_id: 1,
    comment_id: 3,
    content: "대댓글1->3",
    write_date: "2023.01.17 09:54",
    group_id: 1,
    nickname: "홍길동3",
  },
  {
    board_id: 1,
    comment_id: 4,
    content: "오리지널 댓글4",
    write_date: "2023.01.17 09:54",
    group_id: 0,
    nickname: "홍길동4",
  },
  {
    board_id: 1,
    comment_id: 5,
    content: "오리지널 댓글5",
    write_date: "2023.01.17 09:54",
    group_id: 0,
    nickname: "홍길동5",
  },
  {
    board_id: 1,
    comment_id: 6,
    content: "대댓글5->6",
    write_date: "2023.01.17 09:54",
    group_id: 5,
    nickname: "홍길동6",
  },
  {
    board_id: 1,
    comment_id: 7,
    content: "대댓글5->7",
    write_date: "2023.01.17 09:54",
    group_id: 5,
    nickname: "홍길동7",
  },
];

function BoardComment(props) {
  const [inputVisible, setInputVisible] = useState(false);
  const [sendToCommentId, setSendToCommentId] = useState("");

  const origin_comment = DUMMY_DATA.filter(
    (data) => data.board_id === props.board_id && data.group_id === 0
  );

  const openSubCommentInput = (props, e) => {
    setInputVisible(!inputVisible);
    sendToCommentId === props
      ? setSendToCommentId("")
      : setSendToCommentId(props);
  };

  return (
    <div>
      {origin_comment.map((comment) => (
        <>
          <div className="flex flex-col gap-2 p-2 border-b border-black bg-slate-300">
            <span>{comment.nickname}</span>
            <span className="">{comment.content}</span>
            <div className="flex">
              <span className="flex-1">{comment.write_date}</span>
              <button
                className="px-2 bg-[#FA9E13] rounded text-white"
                onClick={(e) =>
                  openSubCommentInput(comment.comment_id, e)
                }
              >
                답글
              </button>
            </div>
          </div>
          {inputVisible && sendToCommentId === comment.comment_id && (
            <BoardSubCommetInput nick={comment.nickname} />
          )}
          <BoardSubComment comment_id={comment.comment_id} />
        </>
      ))}
    </div>
  );
}

export default BoardComment;
