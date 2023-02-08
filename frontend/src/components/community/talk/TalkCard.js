import React from "react";

function TalkCard(props) {
  const popupW = 450;
  const popupH = 600;
  const popupX = window.screen.width / 2 - (popupW - 100) / 2;
  const popupY = window.screen.height / 2 - (popupH + 100) / 2;

  // 새창으로 토론방 띄우기
  const onOpen = () => {
    window.open(
      `http://localhost:3000/community/detail/talk?id=${props.id}`,
      "talk",
      `height=${popupH}, width=${popupW}, left=${popupX}, top=${popupY}`
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
