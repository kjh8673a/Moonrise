import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function ProfilePartyNav() {
  const movePage = useNavigate();

  const [menu, setMenu] = useState("part");
  const selected = "text-white transition-opacity opacity-100";
  const notSelected =
    "text-white transition-opacity opacity-50 hover:opacity-100";

  function changeParticipate() {
    movePage("/profile/list/party/part");
    setMenu("part");
  }

  function changeHost() {
    movePage("/profile/list/party/host");
    setMenu("host");
  }
  return (
    <div className="w-1/3 mb-4">
      <ul className="flex pb-2">
        <li className="flex-1 text-center">
          <button
            className={menu === "part" ? selected : notSelected}
            onClick={changeParticipate}
          >
            참여
          </button>
        </li>
        <li className="flex-1 text-center">
          <button
            className={menu === "host" ? selected : notSelected}
            onClick={changeHost}
          >
            주최
          </button>
        </li>
      </ul>
    </div>
  );
}

export default ProfilePartyNav;
