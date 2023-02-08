import React from "react";

function ProfilePartyCard(props) {
  return (
    <div className="w-48 h-48 rounded-lg">
      <div
        style={{
          backgroundImage: `url(${props.image})`,
          backgroundSize: "cover",
        }}
        className="w-48 h-3/5 rounded-lg relative"
      > 
        {props.status === 'CLOSE' && <div className="w-48 h-full bg-black opacity-80 flex items-center justify-center rounded-lg font-bold"><span className="text-white text-2xl">종료</span></div>}
        {props.location && <div className="absolute bottom-0 right-0 px-2 py-1 bg-[#FA9E13] text-white text-xs">{props.location}</div>}
        {props.online && <div className="absolute bottom-0 right-0 px-2 py-1 bg-[#315B4C] text-white text-xs">온라인</div>}
      </div>
      <div className="w-48 h-2/5 rounded-lg grid grid-rows-3 p-1">
        <div className="row-span-1 text-xs overflow-hidden text-[#FA9E13] flex items-center">
          <span className="whitespace-nowrap text-ellipsis overflow-hidden">
            {props.movie}
          </span>
        </div>
        <div className="row-span-1 overflow-hidden font-semibold text-lg flex items-center">
          <span className="whitespace-nowrap text-ellipsis overflow-hidden">
            {props.title}
          </span>
        </div>
        <div className="row-span-1 text-xs flex items-center">
          {props.moim_date}
        </div>
      </div>
    </div>
  );
}

export default ProfilePartyCard;
