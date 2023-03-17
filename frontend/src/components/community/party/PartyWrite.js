import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

function PartyWrite() {
  const movePage = useNavigate();
  const movieId = useSelector((state) => state.movie.movieId);

  function changeBoard() {
    movePage("/community/list/party");
  }

  const [requestBody, setRequestBody] = useState({
    content: "",
    deadLine: "2023-02-02T06:23:05.082Z",
    location: "",
    meetOnline: true,
    movieId: movieId,
    partyDate: "2023-02-02T06:23:05.082Z",
    partyPeople: 0,
    title: "",
  });

  const access_token = useSelector((state) => state.member.accessToken);

  const titleChangeHandler = (event) => {
    setRequestBody((prevState) => {
      return { ...prevState, title: event.target.value };
    });
  };
  const dateChangeHandler = (event) => {
    setRequestBody((prevState) => {
      return { ...prevState, partyDate: "2023-02-02T06:23:05.082Z" };
    });
  };
  const peopleChangeHandler = (event) => {
    setRequestBody((prevState) => {
      return { ...prevState, partyPeople: event.target.value };
    });
  };
  const contentChangeHandler = (event) => {
    setRequestBody((prevState) => {
      return { ...prevState, content: event.target.value };
    });
  };
  const locationChangeHandler = (event) => {
    setRequestBody((prevState) => {
      return { ...prevState, location: event.target.value };
    });
  };
  const submitParty = async () => {
    console.log(requestBody);
    const config = {
      headers: {
        access_token: access_token,
      },
    };
    axios.post("/api/party/write", requestBody, config).then((response) => {
      console.log(response);
      // changeBoard()
    });
  };
  return (
    <div className="mx-64 mt-10 partyWrite">
      <div className="grid grid-cols-3">
        <div className="col-span-1">
          <button className="mt-2 text-white" onClick={changeBoard}>
            &lt; 이전으로
          </button>
        </div>
        <div className="col-span-1">
          <p className="text-2xl text-center text-white">새로운 뒷풀이</p>
        </div>
      </div>
      <div className="py-2 mt-4 bg-gray-600 rounded-lg bg-opacity-30">
        <div className="grid grid-cols-2">
          <div className="col-span-1">
            <div className="relative m-4">
              <p className="text-gray-300">제목</p>
              <input
                type="text"
                id="title"
                onChange={titleChangeHandler}
                className="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
                placeholder=""
              />
              <label
                for="title"
                className="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
              >
                뒷풀이 제목을 입력해주세요
              </label>
            </div>
            <div className="relative m-4">
              <p className="text-gray-300">날짜</p>
              <input
                type="text"
                id="date"
                onChange={dateChangeHandler}
                className="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
                placeholder=""
              />
              <label
                for="date"
                className="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
              >
                뒷풀이 제목을 입력해주세요
              </label>
            </div>
            <div className="relative m-4">
              <p className="text-gray-300">대표 이미지</p>
              <div className="flex items-center justify-center w-full mt-4">
                <label
                  for="dropzone-file"
                  className="flex flex-col items-center justify-center w-full h-64 border-2 border-white border-dashed rounded-lg cursor-pointer hover:bg-gray-600 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
                >
                  <div className="flex flex-col items-center justify-center pt-5 pb-6">
                    <svg
                      aria-hidden="true"
                      className="w-10 h-10 mb-3 text-gray-400"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"
                      ></path>
                    </svg>
                    <p className="mb-2 text-sm text-gray-500 dark:text-gray-400">
                      Click to upload
                    </p>
                    <p className="text-xs text-white">
                      {" "}
                      파일 형식은 PNG, JPG 만 가능합니다
                    </p>
                  </div>
                  <input id="dropzone-file" type="file" className="hidden" />
                </label>
              </div>
            </div>
          </div>
          <div className="col-span-1">
            <div className="relative m-4">
              <p className="text-gray-300">장소</p>
              <input
                type="text"
                id="title"
                onChange={locationChangeHandler}
                className="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
                placeholder=""
              />
              <label
                for="title"
                className="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
              >
                뒷풀이 제목을 입력해주세요
              </label>
            </div>
            <div className="relative m-4">
              <p className="text-gray-300">인원 수</p>
              <input
                type="text"
                id="title"
                onChange={peopleChangeHandler}
                className="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
                placeholder=""
              />
              <label
                for="title"
                className="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
              >
                뒷풀이 제목을 입력해주세요
              </label>
            </div>
            <div className="relative m-4">
              <p className="text-gray-300">주의 사항</p>
              <textarea
                onChange={contentChangeHandler}
                className="block mt-4 py-2.5 px-2 h-64 w-full resize-none text-sm text-gray-300 bg-transparent border-2 rounded-lg border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
                placeholder=""
              />
            </div>
          </div>
        </div>
        <div className="text-center">
          <button
            onClick={submitParty}
            className="px-4 py-2 bg-orange-300 rounded-lg hover:bg-orange-600 hover:text-white"
          >
            뒷풀이 모집 시작
          </button>
        </div>
      </div>
    </div>
  );
}

export default PartyWrite;
