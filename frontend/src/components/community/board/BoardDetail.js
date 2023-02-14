import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { FaHeart } from "react-icons/fa";
import { Viewer } from "@toast-ui/react-editor"

import BoardComment from "./BoardComment";

function BoardDetail() {
  const [board, setBoard] = useState({});
  const [comment, setComment] = useState("");
  const [writeDate, setWriteDate] = useState("");
  const [isLike, setIsLike] = useState(false);
  const [isBookmark, setIsBookmark] = useState(false);
  const [toastViewer, setToastViewer] = useState(); 
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
        setIsLike(response.data.data.findBoard.isLike);
        setIsBookmark(response.data.data.findBoard.isBookmark);
        setWriteDate(response.data.data.findBoard.dateTime.replace("T", " "));
        setToastViewer(<Viewer initialValue={response.data.data.findBoard.content} /> );
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
        "http://3.35.149.202:80/api/board/comments/create",
        commentRequestBody,
        config
      )
      .then((response) => {
        setComment("");
        reloadBoard();
      });
  };

  const addSubCommentConfirm = () => {
    reloadBoard();
  };

  const editCommentConfirm = () => {
    reloadBoard();
  };

  const getValue = (event) => {
    setCommentRequestBody((prevState) => {
      return { ...prevState, boardId: id, content: event.target.value };
    });
    setComment(event.target.value);
  };

  const comments = board.boardComments;

  const deleteCommentConfirm = () => {
    reloadBoard();
  };

  const createLike = () => {
    axios
      .post(
        "http://3.35.149.202:80/api/board/like",
        {
          boardId: id,
          status: 1,
        },
        config
      )
      .then((response) => {
        console.log(id);
        console.log("좋아요");
        reloadBoard();
      });
  };

  const deleteLike = () => {
    axios
      .post(
        "http://3.35.149.202:80/api/board/like",
        {
          boardId: id,
          status: 0,
        },
        config
      )
      .then((response) => {
        console.log(id);
        console.log("좋아요취소");
        reloadBoard();
      });
  };

  const createBookmark = () => {
    axios
      .post(
        "http://3.35.149.202:80/api/board/bookmark",
        {
          boardId: id,
          status: 1,
        },
        config
      )
      .then((response) => {
        console.log(id);
        console.log("북마크");
        reloadBoard();
      });
  };

  const deleteBookmark = () => {
    axios
      .post(
        "http://3.35.149.202:80/api/board/bookmark",
        {
          boardId: id,
          status: 0,
        },
        config
      )
      .then((response) => {
        console.log(id);
        console.log("북마크취소");
        reloadBoard();
      });
  };

  const deleteBoard = () => {
    const params = new URLSearchParams();
    params.append("boardId", id);
    params.append("statusCode", 3);
    axios
      .post("http://3.35.149.202:80/api/board/status", params, config)
      .then((response) => {
        console.log(id);
        console.log("게시글삭제");
        reloadBoard();
      });
  };

  const reloadBoard = () => {
    axios
      .get("http://3.35.149.202:80/api/board/" + id, config)
      .then((response) => {
        setBoard(response.data.data.findBoard);
        setIsLike(response.data.data.findBoard.isLike);
        setIsBookmark(response.data.data.findBoard.isBookmark);
        setWriteDate(response.data.data.findBoard.dateTime.replace("T", " "));
      });
  };

  return (
    <div className="w-4/5 min-h-screen p-2 m-auto bg-slate-400">
      <div className="flex flex-col items-center border-b">
        <div className="flex w-full ">
          <div className="flex-1 text-left">
            {!isLike && (
              <button
                onClick={createLike}
              >
                <FaHeart/>
              </button>
            )}
            {isLike && (
              <button
                className="rounded-xl bg-[#564E3E] px-2 py-1 m-1 text-white"
                onClick={deleteLike}
              >
                좋아요취소
              </button>
            )}
          </div>
          <span className="text-[#FA9E13] font-semibold flex-1 text-center">
            {movieTitle}
          </span>
          <div className="flex-1 text-right">
            {!isBookmark && (
              <button
                className="bg-[#FA9E13] px-2 py-1 m-1 text-white rounded-xl"
                onClick={createBookmark}
              >
                북마크
              </button>
            )}
            {isBookmark && (
              <button
                className="rounded-xl bg-[#564E3E] px-2 py-1 m-1 text-white"
                onClick={deleteBookmark}
              >
                북마크취소
              </button>
            )}
          </div>
        </div>
        <span className="text-2xl font-extrabold">{board.title}</span>
        <div className="flex w-full">
          <span className="flex-1 cursor-pointer" onClick={goBack}>
            &lt; 이전으로
          </span>
          <span className="flex-1 text-center">{writeDate}</span>
          <span className="flex-1 text-right">
            <button className="px-2 bg-[#B3B6B7] rounded text-white m-1">
              수정
            </button>
            <button
              className="m-1 px-2 bg-[#564E3E] rounded text-white"
              onClick={deleteBoard}
            >
              삭제
            </button>
          </span>
        </div>
      </div>
      <div className="p-2 border-b">
        {toastViewer}
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
