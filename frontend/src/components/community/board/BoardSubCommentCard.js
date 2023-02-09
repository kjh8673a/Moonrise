import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";
import BoardSubCommetInput from "./BoardSubCommetInput";

function BoardSubCommentCard(props) {
  const [inputVisible, setInputVisible] = useState(false);
  const [commentEditVisible, setCommentEditVisible] = useState(false);
  const [sendToCommentId, setSendToCommentId] = useState("");
  const [comment, setComment] = useState(props.content);
  const [commentRequestBody, setCommentRequestBody] = useState({
    content: "",
    memberId: "",
    commentId: props.comment_id,
  });

  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };

  const params = new URLSearchParams();
  params.append("commentId", props.comment_id);
  params.append("statusCode", 3);

  const openSubCommentInput = (props, e) => {
    setInputVisible(!inputVisible);
    sendToCommentId === props
      ? setSendToCommentId("")
      : setSendToCommentId(props);
  };

  const addSubCommentConfirm = () => {
    props.addSubCommentConfirm();
    setInputVisible(false);
  };

  const deleteComment = (event) => {
    event.preventDefault();
    axios
      .post("http://3.35.149.202:80/api/board/comments/status", params, config)
      .then((response) => {
        props.deleteCommentConfirm();
      });
  };

  const editStart = () => {
    setCommentEditVisible(true);
  };

  const editCancel = () => {
    setCommentEditVisible(false);
  };

  const getValue = (event) => {
    setCommentRequestBody((prevState) => {
      return { ...prevState, content: event.target.value };
    });
    setComment(event.target.value);
  };

  const editComment = (event) => {
    event.preventDefault();
    if (comment === "") {
      return;
    }
    axios
      .post(
        "http://3.35.149.202:80/api//board/comments/modify",
        commentRequestBody,
        config
      )
      .then((response) => {
        setComment("");
        setCommentEditVisible(false);
        props.editCommentConfirm();
      });
  };

  return (
    <>
      <div className="flex gap-2 p-2 px-5 bg-gray-400 border-b border-black">
        {props.boardCommentStatus === "DELETED" && (
          <>
            <span>└</span>
            <div className="flex flex-col flex-1 gap-2 ">
              <span></span>
              <span className="">삭제된 댓글입니다</span>
              <div className="flex">
                <span className="flex-1"></span>
              </div>
            </div>
          </>
        )}
        {props.boardCommentStatus === "NORMAL" && !commentEditVisible && (
          <>
            <span>└</span>
            <div className="flex flex-col flex-1 gap-2 ">
              <span>{props.nickname}</span>
              <span className="">{props.content}</span>
              <div className="flex">
                <span className="flex-1">{props.write_date}</span>
                <button
                  className="px-2 bg-[#B3B6B7] rounded text-white"
                  onClick={editStart}
                >
                  수정
                </button>
                <button
                  className="ml-2 px-2 bg-[#564E3E] rounded text-white"
                  onClick={deleteComment}
                >
                  삭제
                </button>
                <button
                  className="ml-2 px-2 bg-[#FA9E13] rounded text-white"
                  onClick={(e) => openSubCommentInput(props.comment_id, e)}
                >
                  답글
                </button>
              </div>
            </div>
          </>
        )}
        {commentEditVisible && (
          <>
            <span>└</span>
            <div className="flex flex-col flex-1 gap-2 ">
              <span>{props.nickname}</span>
              <input
                type="text"
                className="flex-1 p-2 rounded"
                placeholder="내용을 입력해 주세요"
                onChange={getValue}
                value={comment}
              ></input>
              <div className="flex">
                <span className="flex-1"></span>
                <button
                  className="px-2 bg-[#B3B6B7] rounded text-white"
                  onClick={editComment}
                >
                  수정
                </button>
                <button
                  className="ml-2 px-2 bg-[#564E3E] rounded text-white"
                  onClick={editCancel}
                >
                  취소
                </button>
              </div>
            </div>
          </>
        )}
      </div>
      {inputVisible && sendToCommentId === props.comment_id && (
        <BoardSubCommetInput
          nick={props.nickname}
          addSubCommentConfirm={addSubCommentConfirm}
        />
      )}
    </>
  );
}

export default BoardSubCommentCard;
