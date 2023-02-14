import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function ProfileBoardNav() {
  const movePage = useNavigate();
  
  const [menu, setMenu] = useState("bookmark");
  const selected = "text-white transition-opacity opacity-100";
  const notSelected = "text-white transition-opacity opacity-50 hover:opacity-100";



  function changeBookmark() {
    movePage("/profile/list/board/bookmark");
    setMenu("bookmark")
  }
  function changeMy() {
    movePage("/profile/list/board/my");
    setMenu("my")
  }
  return (
    <div className="w-1/3 mb-4">
      <ul className="flex pb-2">
        <li className="flex-1 text-center">
          <button
            className={menu === "bookmark" ? selected : notSelected}
            onClick={changeBookmark}
          >
            북마크
          </button>
        </li>
        <li className="flex-1 text-center">
          <button
            className={menu === "my" ? selected : notSelected}
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
