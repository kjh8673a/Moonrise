import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import BoardComment from "./BoardComment";

function BoardDetail() {
  const [board, setBoard] = useState({});
  const [comment, setComment] = useState("");
  const [commentRequestBody, setCommentRequestBody] = useState({
    boardId: 0,
    content: "",
    groupId: 0,
    isNestedComment: 0,
    memberId: "",
  });
  const movePage = useNavigate();

  const movieTitle = useSelector((state) => state.movie.movieTitle);

  const params = new URLSearchParams(window.location.search);
  const id = parseInt(params.get("id"));

  const access_token = useSelector((state) => state.member.accessToken);

  const config = {
    headers: {
      access_token: access_token,
    },
  };

  useEffect(() => {
    const config = {
      headers: {
        access_token: access_token,
      },
    };
    axios
      .get("http://3.35.149.202:80/api/board/" + id, config)
      .then((response) => {
        setBoard(response.data.data.findBoard);
      });
  }, [id, access_token]);

  const goBack = () => {
    movePage("/community/list/");
  };

  const addComment = (event) => {
    event.preventDefault();
    if (comment === "") {
      return;
    }
    axios
      .post(
        "http://3.35.149.202:80/api//board/comments/create",
        commentRequestBody,
        config
      )
      .then((response) => {
        setComment("");
      });

    axios
      .get("http://3.35.149.202:80/api/board/" + id, config)
      .then((response) => {
        setBoard(response.data.data.findBoard);
      });
  };

  const addSubCommentConfirm = () => {
    axios
      .get("http://3.35.149.202:80/api/board/" + id, config)
      .then((response) => {
        setBoard(response.data.data.findBoard);
      });
  };

  const editCommentConfirm = () => {
    axios
      .get("http://3.35.149.202:80/api/board/" + id, config)
      .then((response) => {
        setBoard(response.data.data.findBoard);
      });
  };

  const getValue = (event) => {
    setCommentRequestBody((prevState) => {
      return { ...prevState, boardId: id, content: event.target.value };
    });
    setComment(event.target.value);
  };

  const comments = board.boardComments;

  const deleteCommentConfirm = () => {
    axios
      .get("http://3.35.149.202:80/api/board/" + id, config)
      .then((response) => {
        setBoard(response.data.data.findBoard);
      });
  };

  return (
    <div className="w-4/5 min-h-screen p-2 m-auto bg-slate-400">
      <div className="flex flex-col items-center border-b">
        <span className="text-[#FA9E13] font-semibold">{movieTitle}</span>
        <span className="text-2xl font-extrabold">{board.title}</span>
        <div className="flex w-full">
          <span className="flex-1 cursor-pointer" onClick={goBack}>
            &lt; 이전으로
          </span>
          <span className="flex-1 text-center">{board.dateTime}</span>
          <span className="flex-1"></span>
        </div>
      </div>
      <div className="p-2 border-b">
        <p>{board.content}</p>
      </div>

      <span>댓글</span>
      <div className="p-2 border-b-2 border-black bg-slate-300">
        <form className="flex gap-2" onSubmit={addComment}>
          <input
            type="text"
            className="flex-1 p-2 rounded"
            placeholder="내용을 입력해 주세요"
            onChange={getValue}
            value={comment}
          ></input>
          <button className="w-20 h-20 bg-[#FA9E13] rounded text-white">
            등록
          </button>
        </form>
      </div>
      {comments &&
        comments.map((comment) => (
          <BoardComment
            id={comment.id}
            groupId={comment.groupId}
            isNestedComment={comment.isNestedComment}
            writeDate={comment.writeDate.replace("T", " ")}
            boardCommentStatus={comment.boardCommentStatus}
            content={comment.content}
            writer={comment.writer}
            addSubCommentConfirm={addSubCommentConfirm}
            deleteCommentConfirm={deleteCommentConfirm}
            editCommentConfirm={editCommentConfirm}
          />
        ))}
    </div>
  );
}

export default BoardDetail;
