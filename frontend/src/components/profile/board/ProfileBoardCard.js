import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";

function ProfileBoardCard(props) {
  const movePage = useNavigate();
  const [imgPath, setImgPath] = useState("");

  const moveBoardDetail = () => {
    movePage(`/community/detail/board?id=${props.board_id}`)
  }

  const tmdbToken = process.env.REACT_APP_TMDB_TOKEN;

  useEffect(() => {
    axios
      .get(
        "https://api.themoviedb.org/3/search/movie?api_key=" +
          tmdbToken +
          "&language=ko-KR&page=1&include_adult=false&query=" +
          props.movie +
          "&page=1"
      ).then((res) => {
        setImgPath("https://image.tmdb.org/t/p/w500" + res.data.results[0].poster_path);
      })
  }, [props.movie, tmdbToken]);

  return (
    <div className="w-48 h-48 rounded-lg cursor-pointer" onClick={moveBoardDetail}>
      <div
        style={{
          backgroundImage: `url(${imgPath})`,
          backgroundSize: "cover",
        }}
        className="w-48 bg-white rounded-lg h-3/5"
      ></div>
      <div className="grid w-48 grid-rows-3 p-1 rounded-lg h-2/5">
        <div className="row-span-1 text-xs overflow-hidden text-[#FA9E13] flex items-center">
          <span className="overflow-hidden whitespace-nowrap text-ellipsis">{props.movie}</span>
        </div>
        <div className="flex items-center row-span-1 overflow-hidden text-lg font-semibold text-white">
          <span className="overflow-hidden whitespace-nowrap text-ellipsis">{props.title}</span>
        </div>
        <div className="flex items-center row-span-1 text-xs text-white">{props.write_date}</div>
      </div>
    </div>
  );
}

export default ProfileBoardCard;
