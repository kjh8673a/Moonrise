import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";
import BoardSubCommetInput from "./BoardSubCommetInput";

function BoardSubCommentCard(props) {
  const [inputVisible, setInputVisible] = useState(false);
  const [commentEditVisible, setCommentEditVisible] = useState(false);
  const [sendToCommentId, setSendToCommentId] = useState("");
  const [comment, setComment] = useState(props.content);
  const nicknameValue = useState(useSelector((state) => state.member.nickname));
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
      <div className="flex gap-2 py-2 px-10 mb-1 ml-auto bg-[#006600] bg-opacity-10 rounded-lg acity-100">
        {props.boardCommentStatus === "DELETED" && (
          <>
            <span>└</span>
            <div className="flex flex-col flex-1 gap-2 ">
              <span></span>
              <span className="font-medium">삭제된 댓글입니다</span>
              <div className="flex">
                <span className="flex-1"></span>
              </div>
            </div>
          </>
        )}
        {props.boardCommentStatus === "NORMAL" && !commentEditVisible && (
          <>
            <span>└</span>
            <div className="relative flex flex-col flex-1 gap-2">
              <div>
                <span className="font-semibold">{props.nickname}</span>
                <span className="flex-1 ml-2 text-xs">{props.write_date}</span>
              </div>
              <span className="ml-5 font-medium">{props.content}</span>
              <div className="absolute bottom-0 right-0">
                {nicknameValue[0] === props.nickname && (
                  <>
                    <button
                      className="px-2 bg-[#646564] rounded text-white"
                      onClick={editStart}
                    >
                      수정
                    </button>
                    <button
                      className="ml-2 px-2 bg-[#51401f] rounded text-white"
                      onClick={deleteComment}
                    >
                      삭제
                    </button>
                  </>
                )}

                <button
                  className="ml-2 px-2 bg-[#b95a27] rounded text-white font-medium"
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
