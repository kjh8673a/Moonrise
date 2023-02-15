import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { FaHeart, FaRegHeart, FaBookmark, FaRegBookmark } from "react-icons/fa";
import { Viewer } from "@toast-ui/react-editor";

import BoardComment from "./BoardComment";
import MainNav from "../../common/MainNav";
import BoardEdit from "./BoardEdit";

function BoardDetail() {
  const [board, setBoard] = useState({});
  const [comment, setComment] = useState("");
  const [writeDate, setWriteDate] = useState("");
  const [isLike, setIsLike] = useState(false);
  const [isBookmark, setIsBookmark] = useState(false);
  const [toastViewer, setToastViewer] = useState();
  const [content, setContent] = useState("");
  const [onEdit, setOnEdit] = useState(false);
  const [writer, setWriter] = useState("");
  const nicknameValue = useState(useSelector((state) => state.member.nickname));
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
        setWriter(response.data.data.findBoard.writer);
        setBoard(response.data.data.findBoard);
        setIsLike(response.data.data.findBoard.isLike);
        setIsBookmark(response.data.data.findBoard.isBookmark);
        setWriteDate(response.data.data.findBoard.dateTime.replace("T", " "));
        setToastViewer(
          <Viewer initialValue={response.data.data.findBoard.content} />
        );
        setContent(response.data.data.findBoard.content);
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
        movePage("/community/list/");
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
        setToastViewer(
          <Viewer initialValue={response.data.data.findBoard.content} />
        );
        setContent(response.data.data.findBoard.content);
      });
  };

  const openEdit = () => {
    setOnEdit(true);
  };
  const closeEdit = () => {
    setOnEdit(false);
  };

  return (
    <>
      {onEdit && (
        <BoardEdit
          closeEdit={closeEdit}
          content={content}
          title={board.title}
          boardId={id}
        />
      )}
      {!onEdit && (
        <>
          <MainNav />
          <div className="flex flex-col items-center w-4/5 p-2 m-auto mb-2 text-white">
            <div className="flex w-full ">
              <div className="flex-1 text-left">
                {!isLike && (
                  <button onClick={createLike}>
                    <FaRegHeart className="text-[#cb3131]" size="25" />
                  </button>
                )}
                {isLike && (
                  <button onClick={deleteLike}>
                    <FaHeart className="text-[#cb3131]" size="25" />
                  </button>
                )}
              </div>
              <span className="text-[#FA9E13] font-semibold flex-1 text-center">
                {movieTitle}
              </span>
              <div className="relative flex-1 text-right">
                {!isBookmark && (
                  <button onClick={createBookmark}>
                    <FaRegBookmark className="text-[#f1c243]" size="25" />
                  </button>
                )}
                {isBookmark && (
                  <button onClick={deleteBookmark}>
                    <FaBookmark className="text-[#f1c243]" size="25" />
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
                {nicknameValue[0] === writer && (
                  <>
                    <button
                      className="px-2 bg-[#646564] rounded text-white m-1 font-semibold"
                      onClick={openEdit}
                    >
                      수정
                    </button>
                    <button
                      className="m-1 px-2 bg-[#51401f] rounded text-white font-semibold"
                      onClick={deleteBoard}
                    >
                      삭제
                    </button>
                  </>
                )}
              </span>
            </div>
          </div>
          <div className="w-4/5 mb-5 p-2 m-auto bg-[#315B4C] bg-opacity-90 rounded-lg">
            <div className="p-2 mb-2 bg-white border-b rounded-lg bg-opacity-80">
              {toastViewer}
            </div>

            <div className="p-2 mb-1 bg-[#B3B6B7] bg-opacity-90 rounded-lg ">
              <form className="flex gap-2" onSubmit={addComment}>
                <input
                  type="text"
                  className="flex-1 p-2 rounded"
                  placeholder="내용을 입력해 주세요"
                  onChange={getValue}
                  value={comment}
                ></input>
                <button className="w-20 h-20 bg-[#fdca00] rounded text-white font-semibold">
                  등록
                </button>
              </form>
            </div>
            <div className="p-2 mb-1 bg-[#B3B6B7] bg-opacity-90 rounded-lg">
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
          </div>
        </>
      )}
    </>
  );
}

export default BoardDetail;
