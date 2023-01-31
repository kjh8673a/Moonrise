import React from "react";

function TalkCard(props) {

  // 새창으로 토론방 띄우기
  const onOpen = () => {
    console.log(props.title);
    window.open(
      "http://localhost:3000/community/detail/talk",
      "_blank",
      "height=650, width=400"
    );
  };

  return (
    <div>
      <li
        className="px-10 py-2 text-center cursor-pointer bg-slate-300"
        onClick={onOpen}
      >
        <div className="overflow-hidden text-lg font-bold mb-7 text-ellipsis whitespace-nowrap">
          {props.title}
        </div>
        <div>{props.latest}</div>
      </li>
    </div>
  );
}

export default TalkCard;
