import React from "react";

function BoardCard(props) {
  return (
    <li className="block float-left border-b-8 bg-slate-500 h-50">
      <div className="float-left w-1/5 m-auto overflow-hidden h-50">
        <img className="object-fill w-40 h-40 m-auto" src={props.poster} alt="poster" />
      </div>
      <div className="float-left w-4/5 ">
        <div className="h-10 leading-10">
          <span className="text-2xl font-bold">{props.title}</span>
          <span className="float-right">{props.nickname}</span>
        </div>
        <div className="overflow-hidden break-words h-30 text-ellipsis">
          <p className="line-clamp-3">{props.content}</p>
        </div>
        <div className="h-10 leading-10">
          <span>{props.write_date}</span>
          <span className="float-right">
            좋아요 {props.like_cnt} 댓글 {props.comment_cnt}
          </span>
        </div>
      </div>
    </li>
  );
}

export default BoardCard;
