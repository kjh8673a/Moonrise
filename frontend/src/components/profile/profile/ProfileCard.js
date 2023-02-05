import React from "react";

import Profile from "../../../assets/img/profile.png";

function ProfileCard() {
  return (
    <div className="grid h-full grid-rows-6 p-5">
      <div className="row-span-2 text-center border-b">
        <img
          className="m-auto rounded-full h-36 w-36 border-1"
          src={Profile}
          alt=""
        />
        <span className="text-xl font-bold">남극도둑갈매기</span>
        <button>수정</button>
      </div>
      <div className="row-span-3 border-b"></div>
      <div className="flex items-center justify-center row-span-1">
        <div className="text-xl font-bold ">달:뜸</div>
      </div>
    </div>
  );
}

export default ProfileCard;
