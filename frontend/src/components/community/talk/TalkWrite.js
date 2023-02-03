import React from "react";
import { useNavigate } from "react-router-dom";

function TalkWrite() {
  const movePage = useNavigate();

  const goBack = () => {
    movePage("/community/list/talk");
  };

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
        <div className="m-4">
          <span>제목</span>
          <input
            type="text"
            id="title"
            class="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
            placeholder=""
          />
          <label
            for="title"
            class="absolute text-sm text-gray-300 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-orange-300 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >
            뒷풀이 제목을 입력해주세요
          </label>
        </div>
        <div className="m-4">
          <span>내용</span>
          <textarea
            class="block mt-4 py-2.5 px-2 h-44 w-full resize-none text-sm text-gray-300 bg-transparent border-2 rounded-lg border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer"
            placeholder=""
          />
        </div>
        <div className="text-center">
          <button className="rounded-lg px-4 py-2 bg-orange-300 hover:bg-orange-600 hover:text-white">
            담소 등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default TalkWrite;
