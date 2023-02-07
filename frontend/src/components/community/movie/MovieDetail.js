import React, { useState } from "react";
import { useSelector } from "react-redux";
import MovieRatingDetailModal from "./MovieRatingDetailModal";

function MovieDetail() {
  const rating = [4.8, 3.5, 4.0, 4.3, 3.9];
  const average = rating.reduce((a, c) => a + c) / rating.length;
  const [ratingDetailModalOpen, setRatingDetailModalOpen] = useState(false);

  const showRatingDetailModal = () => {
    setRatingDetailModalOpen(true);
  };

  const data = useSelector((state) => state.movie);

  return (
    <div className="my-5 MovieDetail">
      <ul>
        <li>
          <img
            className="w-3/5 ml-20"
            src={useSelector((state) => state.movie.moviePoster)}
            alt="poster"
          />
        </li>
        <li className="mb-3 text-3xl font-bold text-white">
          {useSelector((state) => state.movie.movieTitle)}
        </li>
        <p className="text-sm text-white">
          {average} <button onClick={showRatingDetailModal}>평점상세</button><button>평가하기 </button>
        </p>
        {ratingDetailModalOpen && (
          <MovieRatingDetailModal
            setRatingDetailModalOpen={setRatingDetailModalOpen}
            movieId={data.movieId}
            movieTitle={data.movieTitle}
          />
        )}
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
