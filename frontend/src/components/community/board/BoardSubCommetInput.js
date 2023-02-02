import React, { useState } from "react";

function BoardSubCommetInput(props) {
    const [commentValue, setCommentValue] = useState("@" + props.nick + " ");

    const addComment = (event) => {
        event.preventDefault();
      }
    
      const getValue = (event) => {
        let { value } = { ...event.target };
        setCommentValue(value);
      }
    

  return (
    <div className=" flex gap-2 p-2 border-b-2 px-5 border-black bg-gray-400">
      <span>└</span>
      <form className=" flex-1 flex gap-2" onSubmit={addComment}>
        <input
          type="text"
          className="flex-1 rounded p-2"
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
