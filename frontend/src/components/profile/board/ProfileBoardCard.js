import React from "react";

function ProfileBoardCard(props) {
  return (
    <div className="w-48 h-48 rounded-lg">
      <div
        style={{
          backgroundImage: `url(${props.image})`,
          backgroundSize: "cover",
        }}
        className="w-48 h-3/5 rounded-lg"
      ></div>
      <div className="w-48 h-2/5 rounded-lg grid grid-rows-3 p-1">
        <div className="row-span-1 text-xs overflow-hidden text-[#FA9E13] flex items-center">
          <span className="whitespace-nowrap text-ellipsis overflow-hidden">{props.movie}</span>
        </div>
        <div className="row-span-1 overflow-hidden font-semibold text-lg flex items-center text-white">
          <span className="whitespace-nowrap text-ellipsis overflow-hidden">{props.title}</span>
        </div>
        <div className="row-span-1 text-xs flex items-center">{props.write_date}</div>
      </div>
    </div>
  );
}

export default ProfileBoardCard;
