import React from "react";

function ProfileCard(props) {
  return (
    <div className="grid h-full grid-rows-6 p-5">
      <div className="row-span-2 text-center border-b">
        <img
          className="m-auto rounded-full h-32 w-32 border-1"
          src='http://ojsfile.ohmynews.com/AT_T_IMG/2018/0420/A0002426619_T.jpg'
          alt=""
        />
        <span className="text-xl font-bold">남극도둑갈매기</span>
        <button onClick={props.openEditor}>수정</button>
      </div>
      <div className="row-span-3 border-b"></div>
      <div className="flex items-center justify-center row-span-1">
        <div className="text-xl font-bold ">달:뜸</div>
      </div>
    </div>
  );
}

export default ProfileCard;
