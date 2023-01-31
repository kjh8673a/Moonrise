import React from "react";

function BoardCard(props) {
  return (
    <div className="BoardCard grid grid-cols-5 gap-2 border-b-2 border-b-white pb-2 mb-2">
      <div className="col-span-1">
        <img className="object-fill pr-2" src={props.poster} alt="poster" />
      </div>
      <div className="col-span-4">
        <div className="flex justify-between">
          <span className="text-2xl text-white font-bold">{props.title}</span>
          <span className="text-white">{props.nickname}</span>
        </div>
        <div className="my-3">
          <p className="text-white line-clamp-3">{props.content}</p>
        </div>
        <div className="flex justify-between">
          <span className="text-white text-sm">{props.write_date}</span>
          <span className="text-white text-sm">
            좋아요 {props.like_cnt} 댓글 {props.comment_cnt}
          </span>
        </div>
      </div>
    </div>
  );
}

export default BoardCard;
