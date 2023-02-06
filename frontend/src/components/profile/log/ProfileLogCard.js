import React, { useState } from "react";

function ProfileLogCard(props) {
  const [hide, setHide] = useState(true);

  return (
    <div
      style={{
        backgroundImage: `url(${props.image})`,
        backgroundSize: "cover",
      }}
      className="w-48 h-48 p-2 relative rounded-lg"
      onMouseEnter={() => {
        setHide(false);
      }}
      onMouseLeave={() => {
        setHide(true);
      }}
    >
      {!hide && (
        <div className="rounded-lg p-2 flex items-center justify-center w-48 h-48 bg-black opacity-70 top-0 left-0 absolute">
          <span className="text-white font-medium text-xl">{props.title}</span>
        </div>
      )}

      {props.type === "BOARD" && (
        <div className="bg-[#FA9E13] w-20 text-center rounded-xl">
          <span className="font-medium text-white text-sm">게시글</span>
        </div>
      )}
      {props.type === "TALK" && (
        <div className="bg-[#564E3E] w-20 text-center rounded-xl">
          <span className="font-medium text-white text-sm">담소</span>
        </div>
      )}
      {props.type === "PARTY" && (
        <div className="bg-[#315B4C] w-20 text-center rounded-xl">
          <span className="font-medium text-white text-sm">뒷풀이</span>
        </div>
      )}
    </div>
  );
}

export default ProfileLogCard;
