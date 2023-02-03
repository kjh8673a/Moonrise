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

  return (
    <>
      <div className="flex p-2 px-5 gap-2 border-b border-black bg-gray-400">
        <span>└</span>
        <div className=" flex-1 flex flex-col gap-2">
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
        <BoardSubCommetInput nick={props.nickname} />
      )}
    </>
  );
}

export default BoardSubCommentCard;
