import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

function TalkWrite() {
  const [requestBody, setRequestBody] = useState({
    description:"",
    maxppl : 0,
    movieId : useSelector(state => state.movie.movieId),
    title : "",
  });
  const access_token = useSelector(state=> state.member.accessToken); 
  const baseURL = process.env.REACT_APP_BASE_URL;
  const config = { 
    headers: {
      "access_token": access_token,
    }
  };
  const movePage = useNavigate();

  const goBack = () => {
    movePage("/community/list/talk");
  };

  function titleHandler (event) {
    setRequestBody({...requestBody, title: event.target.value})
  }
  function pplHandler (event) {
    setRequestBody({...requestBody, maxppl: event.target.value})
  }
  function contentHandler (event) {
    setRequestBody({...requestBody, description: event.target.value})
  }

  function writeDebate(){
    axios.post(baseURL + "/api/debate/create", requestBody, config)
    .then(goBack);
  }
  return (
    <div className="mx-64 mt-10 text-white">
      <div className=" grid grid-cols-3 py-3">
        <span
          className="cursor-pointer col-span-1 table-column align-bottom"
          onClick={goBack}
        >
          &lt; 이전으로
        </span>
        <span className="col-span-1 text-center text-2xl">
          새로운 담소 만들기
        </span>
      </div>
      <div className="bg-gray-600 bg-opacity-30 rounded-lg mt-4 py-2">
        <div className="grid grid-cols-4 gap-4 m-4">
          <div className="col-span-3 ">
            <span>제목</span>
            <input
              type="text"
              id="title"
              onChange={titleHandler}
              className="block p-2 text-sm w-full text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-orange-300"
              placeholder=""
            />
          </div>
          <div className="col-span-1">
            <span>인원수</span>
            <input
              onChange={pplHandler}
              type="number"
              id="title"
              className="block p-2 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 focus:outline-none focus:ring-0 focus:border-orange-300"
              placeholder=""
            />
          </div>
        </div>
        <div className="m-4">
          <span>내용</span>
          <textarea
            onChange={contentHandler}
            className="block mt-4 py-2.5 px-2 h-44 w-full resize-none text-sm text-gray-300 bg-transparent border-2 rounded-lg border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
            placeholder=""
          />
        </div>
        <div className="text-center">
          <button onClick={writeDebate} className="rounded-lg px-4 py-2 bg-orange-300 hover:bg-orange-600 hover:text-white">
            담소 등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default TalkWrite;
