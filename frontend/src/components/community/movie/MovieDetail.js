import React, { useState } from "react";
import { useSelector } from "react-redux";
import MovieRatingModal from "./MovieRatingModal";

function MovieDetail() {
  const rating = [4.8, 3.5, 4.0, 4.3, 3.9];
  const DUMMY_DATA = [4.8, 3.5, 4.0, 4.3, 3.9];
  const average = rating.reduce((a, c) => a + c) / rating.length;
  const [haveRating, setHaveRating] = useState(false);
  const [ratingDetailModalOpen, setRatingDetailModalOpen] = useState(false);
  const [ratingCreateModalOpen, setRatingCreateModalOpen] = useState(false);
  const [ratingEditModalOpen, setRatingEditModalOpen] = useState(false);

  const showRatingDetailModal = () => {
    setRatingDetailModalOpen(true);
  };

  const showRatingCreateModal = () => {
    setRatingCreateModalOpen(true);
  };
  
  const showRatingEditModal = () => {
    setRatingEditModalOpen(true);
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
          {average} <button onClick={showRatingDetailModal}>평점상세</button>
          {!haveRating && (
            <button onClick={showRatingCreateModal}>평가하기</button>
          )}
          {haveRating && (
            <button onClick={showRatingEditModal}>평가하기</button>
          )}
        </p>
        {ratingDetailModalOpen && (
          <MovieRatingModal
            type="DETAIL"
            setRatingDetailModalOpen={setRatingDetailModalOpen}
            movieId={data.movieId}
            movieTitle={data.movieTitle}
            story={rating[0]}
            acting={rating[1]}
            direction={rating[2]}
            visual={rating[3]}
            sound={rating[4]}
          />
        )}
        {ratingCreateModalOpen && (
          <MovieRatingModal
            type="CREATE"
            setRatingCreateModalOpen={setRatingCreateModalOpen}
            movieId={data.movieId}
            movieTitle={data.movieTitle}
          />
        )}
        {ratingEditModalOpen && (
          <MovieRatingModal
            type="EDIT"
            setRatingEditModalOpen={setRatingEditModalOpen}
            movieId={data.movieId}
            movieTitle={data.movieTitle}
            story={DUMMY_DATA[0]}
            acting={DUMMY_DATA[1]}
            direction={DUMMY_DATA[2]}
            visual={DUMMY_DATA[3]}
            sound={DUMMY_DATA[4]}
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
