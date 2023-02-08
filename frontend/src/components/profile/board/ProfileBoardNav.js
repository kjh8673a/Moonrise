import React from "react";
import { useNavigate } from "react-router-dom";

function ProfileBoardNav() {
  const movePage = useNavigate();

  function changeBookmark() {
    movePage("/profile/list/board/bookmark");
  }
  function changeMy() {
    movePage("/profile/list/board/my");
  }
  return (
    <div className="mb-4 w-1/3">
      <ul className="flex pb-2">
        <li className="flex-1 text-center">
          <button
            className="text-gray-400 hover:text-white hover:border-b"
            onClick={changeBookmark}
          >
            북마크
          </button>
        </li>
        <li className="flex-1 text-center">
          <button
            className="text-gray-400 hover:text-white hover:border-b"
            onClick={changeMy}
          >
            내 작성
          </button>
        </li>
      </ul>
    </div>
  );
}

export default ProfileBoardNav;
