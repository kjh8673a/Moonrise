import React from "react";
import { useNavigate } from "react-router-dom";

function ProfilePartyNav() {
  const movePage = useNavigate();

  function changeParticipate() {
    movePage("/profile/list/party/part");
  }

  function changeHost() {
    movePage("/profile/list/party/host");
  }
  return (
    <div className="mb-4 w-1/3">
      <ul className="flex pb-2">
        <li className="flex-1 text-center">
          <button
            className="text-gray-400 hover:text-white hover:border-b"
            onClick={changeParticipate}
          >
            참여
          </button>
        </li>
        <li className="flex-1 text-center">
          <button
            className="text-gray-400 hover:text-white hover:border-b"
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
