import React from "react";
import { useNavigate } from "react-router-dom";

function BoardCard(props) {
  const movePage = useNavigate();
  
  function goDetail(){
    movePage(`/community/detail/board?id=${props.id}`);
  }

  return (
    <div className="grid grid-cols-5 gap-2 pb-2 mb-2 border-b-2 cursor-pointer BoardCard border-b-white" onClick={goDetail}>
      <div className="col-span-1">
        <img className="object-fill pr-2" src={props.poster} alt="poster" />
      </div>
      <div className="col-span-4">
        <div className="flex justify-between">
          <span className="text-2xl font-bold text-white">{props.title}</span>
          <span className="text-white">{props.nickname}</span>
        </div>
        <div className="my-3">
          <p className="text-white line-clamp-3">{props.content}</p>
        </div>
        <div className="flex justify-between">
          <span className="text-sm text-white">{props.write_date}</span>
          <span className="text-sm text-white">
            좋아요 {props.like_cnt} 댓글 {props.comment_cnt}
          </span>
        </div>
      </div>
    </div>
  );
}

export default BoardCard;
