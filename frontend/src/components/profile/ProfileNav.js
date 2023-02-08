import React from "react";
import { useNavigate } from "react-router-dom";

function ProfileNav() {
  const movePage = useNavigate();

  function changeLog() {
    movePage("/profile/list/");
  }
  function changeBoard() {
    movePage("/profile/list/board/bookmark");
  }
  function changeParty() {
    movePage("/profile/list/party/part");
  }
  return (
    <div className="mb-4">
      <ul class="navbar-nav flex border-b pb-2">
        <li class="flex-1 text-center">
          <button class="text-gray-400 hover:text-white" onClick={changeLog}>
            활동기록
          </button>
        </li>
        <li class="flex-1 text-center">
          <button class="text-gray-400 hover:text-white" onClick={changeBoard}>
            게시글
          </button>
        </li>
        <li class="flex-1 text-center">
          <button class="text-gray-400 hover:text-white" onClick={changeParty}>
            뒷풀이
          </button>
        </li>
      </ul>
    </div>
  );
}

export default ProfileNav;
