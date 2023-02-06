import React from "react";
import { useSelector } from "react-redux";

function MovieDetail() {
  const rating = [4.8, 3.5, 4.0, 4.3, 3.9];
  const average = rating.reduce((a, c) => a + c) / rating.length;
  return (
    <div className="my-5 MovieDetail">
      <ul>
        <li>
          <img className="w-3/5 ml-20" src={useSelector(state => state.movie.moviePoster)} alt="poster" />
        </li>
        <li className="mb-3 text-3xl font-bold text-white">{useSelector(state => state.movie.movieTitle)}</li>
        <p className="text-sm text-white">{average} 평점상세 평가하기</p>
        <p className="overflow-hidden text-gray-300 text-ellipsis whitespace-nowrap">
          <b className="text-lg text-white">감독</b> 
        </p>
        <p className="overflow-hidden text-gray-300 text-ellipsis whitespace-nowrap">
          <b className="text-lg text-white">주연</b> 
        </p>
        <p className="overflow-hidden text-gray-300 text-ellipsis whitespace-nowrap">
          <b className="text-lg text-white">장르</b> 
        </p>
      </ul>
    </div>
  );
}

export default MovieDetail;
