import React, { useState } from "react";
import BoardSubCommetInput from "./BoardSubCommetInput";

function BoardSubCommentCard(props) {
  const [inputVisible, setInputVisible] = useState(false);
  const [sendToCommentId, setSendToCommentId] = useState("");

  const openSubCommentInput = (props, e) => {
    setInputVisible(!inputVisible);
    sendToCommentId === props
      ? setSendToCommentId("")
      : setSendToCommentId(props);
  };

  const addSubCommentConfirm = () => {
    props.addSubCommentConfirm();
  };

  return (
    <>
      <div className="flex gap-2 p-2 px-5 bg-gray-400 border-b border-black">
        <span>└</span>
        <div className="flex flex-col flex-1 gap-2 ">
          <span>{props.nickname}</span>
          <span className="">{props.content}</span>
          <div className="flex">
            <span className="flex-1">{props.write_date}</span>
            <button
              className="px-2 bg-[#FA9E13] rounded text-white"
              onClick={(e) => openSubCommentInput(props.comment_id, e)}
            >
              답글
            </button>
          </div>
        </div>
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
