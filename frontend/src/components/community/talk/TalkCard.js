import React from "react";

function TalkCard(props) {

  // 새창으로 토론방 띄우기
  const onOpen = () => {
    console.log(props.title);
    window.open(
      "http://localhost:3000/TalkView.js",
      "_blank",
      "height=650, width=400"
    );
  };

  return (
    <div>
      <li
        className="bg-slate-300 text-center py-2 px-10 cursor-pointer"
        onClick={onOpen}
      >
        <div className="font-bold mb-7 text-lg overflow-hidden text-ellipsis whitespace-nowrap">
          {props.title}
        </div>
        <div>{props.latest}</div>
      </li>
    </div>
  );
}

export default TalkCard;
