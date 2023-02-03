import React from "react";
import { useSelector } from "react-redux";

function MovieDetail() {
  const rating = [4.8, 3.5, 4.0, 4.3, 3.9];
  const average = rating.reduce((a, c) => a + c) / rating.length;
  return (
    <div className="MovieDetail my-5">
      <ul>
        <li>
          <img className="w-3/4 m-auto" src={useSelector(state => state.movie.moviePoster)} alt="poster" />
        </li>
        <li className="text-white text-3xl font-bold mb-3">{useSelector(state => state.movie.movieTitle)}</li>
        <p className="text-white text-sm">{average} 평점상세 평가하기</p>
        <p className="text-gray-300  overflow-hidden text-ellipsis whitespace-nowrap">
          <b className="text-white text-lg">감독</b> 
        </p>
        <p className="text-gray-300 overflow-hidden text-ellipsis whitespace-nowrap">
          <b className="text-white text-lg">주연</b> 
        </p>
        <p className="text-gray-300  overflow-hidden text-ellipsis whitespace-nowrap">
          <b className="text-white text-lg">장르</b> 
        </p>
      </ul>
    </div>
  );
}

export default MovieDetail;
