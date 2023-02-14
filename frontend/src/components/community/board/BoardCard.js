import React from "react";
import { useNavigate } from "react-router-dom";

function BoardCard(props) {
  const movePage = useNavigate();

  function goDetail() {
    movePage(`/community/detail/board?id=${props.id}`);
  }

  return (
    <div
      className="grid grid-cols-4 gap-2 p-4 mb-2  text-white cursor-pointer bg-[#B3B6B7] bg-opacity-30 rounded-xl BoardCard hover:bg-[#dbde2c] hover:text-black "
      onClick={goDetail}
    >
      <div className="col-span-4">
        <div className="flex justify-between">
          <span className="text-2xl font-bold">{props.title}</span>
          <span className="">{props.nickname}</span>
        </div>
        <div className="flex justify-between">
          <span className="text-sm">{props.write_date}</span>
          <span className="text-sm">
            좋아요 {props.like_cnt} &nbsp; 조회수 {props.view_cnt}
          </span>
        </div>
      </div>
    </div>
  );
}

export default BoardCard;
