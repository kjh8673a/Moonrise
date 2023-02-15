import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";

function BoardSubCommetInput(props) {
  const [commentValue, setCommentValue] = useState("");

  const params = new URLSearchParams(window.location.search);
  const id = parseInt(params.get("id"));

  const baseURL = process.env.REACT_APP_BASE_URL;
  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };
  
  const [commentRequestBody, setCommentRequestBody] = useState({
    boardId: 0,
    content: "",
    groupId: 0,
    isNestedComment: 1,
    memberId: "",
  });
  
  const addSubComment = (event) => {
    event.preventDefault();
    if(commentValue === "") {
      return;
    }
    console.log(commentRequestBody)
    axios
      .post(
        baseURL + "/api//board/comments/create",
        commentRequestBody,
        config
      )
      .then((response) => {
        setCommentValue("");
        props.addSubCommentConfirm();
      });
    
    
  };

  const getValue = (event) => {
    setCommentRequestBody((prevState) => {
      return { ...prevState, boardId: id, content: event.target.value, groupId: props.comment_id };
    });
    setCommentValue(event.target.value);
  };

  return (
    <div className="flex gap-2 p-2 px-5 bg-[#006600] bg-opacity-10 rounded-lg mb-1">
      <span>└</span>
      <form className="flex flex-1 gap-2 " onSubmit={addSubComment}>
        <input
          type="text"
          className="flex-1 p-2 rounded"
          placeholder="내용을 입력해 주세요"
          value={commentValue}
          onChange={getValue}
        ></input>
        <button className="w-20 h-20 bg-[#fdca00] rounded text-white font-semibold">
          등록
        </button>
      </form>
    </div>
  );
}

export default BoardSubCommetInput;
