import React, { useState } from "react";

function BoardSubCommetInput(props) {
  const [commentValue, setCommentValue] = useState("@" + props.nick + " ");

  const addSubComment = (event) => {
    event.preventDefault();
    props.addSubCommentConfirm();
  };

  const getValue = (event) => {
    let { value } = { ...event.target };
    setCommentValue(value);
  };

  return (
    <div className="flex gap-2 p-2 px-5 bg-gray-400 border-b-2 border-black ">
      <span>└</span>
      <form className="flex flex-1 gap-2 " onSubmit={addSubComment}>
        <input
          type="text"
          className="flex-1 p-2 rounded"
          placeholder="내용을 입력해 주세요"
          value={commentValue}
          onChange={getValue}
        ></input>
        <button className="w-20 h-20 bg-[#FA9E13] rounded text-white">
          등록
        </button>
      </form>
    </div>
  );
}

export default BoardSubCommetInput;
