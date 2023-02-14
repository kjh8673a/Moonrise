import React from "react";
import { useNavigate } from "react-router-dom";

function ProfilePartyCard(props) {
  const movePage = useNavigate();
  
  const movePartyDetail = () => {
    movePage('/community/detail/party/'+props.moim_id);
  }
  
  var today = new Date();

  var year = today.getFullYear();
  var month = ('0' + (today.getMonth() + 1)).slice(-2);
  var day = ('0' + today.getDate()).slice(-2);

  var dateString = year + '-' + month  + '-' + day;
  
  var hours = ('0' + today.getHours()).slice(-2); 
  var minutes = ('0' + today.getMinutes()).slice(-2);
  var seconds = ('0' + today.getSeconds()).slice(-2); 

  var timeString = hours + ':' + minutes  + ':' + seconds;

  return (
    <div className="w-48 h-48 rounded-lg cursor-pointer" onClick={movePartyDetail}>
      <div
        style={{
          backgroundImage: `url(${props.image})`,
          backgroundSize: "cover",
        }}
        className="relative w-48 rounded-lg h-3/5"
      > 
        {props.moim_date < dateString + "T" +timeString && <div className="flex items-center justify-center w-48 h-full font-bold bg-black rounded-lg opacity-80"><span className="text-2xl text-white">종료</span></div>}
        <div className="absolute bottom-0 right-0 px-2 py-1 bg-[#FA9E13] text-white text-xs">{props.location}</div>
      </div>
      <div className="grid w-48 grid-rows-2 p-1 rounded-lg h-2/5">
        <div className="flex items-center row-span-1 overflow-hidden text-lg font-semibold">
          <span className="overflow-hidden whitespace-nowrap text-ellipsis">
            {props.title}
          </span>
        </div>
        <div className="flex items-center row-span-1 text-xs">
          {props.moim_date.replace("T", " ")}
        </div>
      </div>
    </div>
  );
}

export default ProfilePartyCard;
