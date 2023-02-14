import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function ProfileNav() {
  const movePage = useNavigate();

  const [menu, setMenu] = useState("bookmark");
  const selected = "text-white transition-opacity opacity-100";
  const notSelected = "text-white transition-opacity opacity-50 hover:opacity-100";

  function changeBoard() {
    movePage("/profile/list/board/bookmark");
    setMenu("bookmark")
  }
  function changeParty() {
    movePage("/profile/list/party/part");
    setMenu("party")
  }
  return (
    <div className="mb-4">
      <ul className="flex pb-2 border-b navbar-nav">
        <li className="flex-1 text-center">
          <button className={menu === "bookmark" ? selected : notSelected} onClick={changeBoard}>
            게시글
          </button>
        </li>
        <li className="flex-1 text-center">
          <button className={menu === "party" ? selected : notSelected} onClick={changeParty}>
            뒷풀이
          </button>
        </li>
      </ul>
    </div>
  );
}

export default ProfileNav;
