import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import MovieRatingModal from "./MovieRatingModal";

function MovieDetail() {
  const [rating, setRating] = useState([0, 0, 0, 0, 0, 0, 0]);
  const [myRating, setMyRating] = useState([0, 0, 0, 0, 0, 0]);
  const [average, setAverage] = useState(0);
  const [haveRating, setHaveRating] = useState(false);
  const [ratingDetailModalOpen, setRatingDetailModalOpen] = useState(false);
  const [ratingCreateModalOpen, setRatingCreateModalOpen] = useState(false);
  const [ratingEditModalOpen, setRatingEditModalOpen] = useState(false);

  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };

  const data = useSelector((state) => state.movie);
  useEffect(() => {
    const config = {
      headers: {
        access_token: access_token,
      },
    };
    axios
      .get("http://3.35.149.202:80/api/rating/find/" + data.movieId, config)
      .then((response) => {
        console.log(response.data);
        if (response != null) {
          setRating(response.data);
          setAverage(response.data[6] / (response.data[0] * 5));
        }
      });
    axios
      .get(
        "http://3.35.149.202:80/api/rating/personal?movieId=" + data.movieId,
        config
      )
      .then((response) => {
        console.log(response);
        if (response != null) {
          setMyRating(response.data);
          setHaveRating(true);
        } else {
          setHaveRating(false);
        }
      });
  }, [data.movieId, access_token]);

  const showRatingDetailModal = () => {
    setRatingDetailModalOpen(true);
  };

  const showRatingCreateModal = () => {
    setRatingCreateModalOpen(true);
  };

  const showRatingEditModal = () => {
    setRatingEditModalOpen(true);
  };

  const createRatingConfirm = () => {
    roadRating();
  };

  const editRatingConfirm = () => {
    roadRating();
  };

  const roadRating = () => {
    axios
      .get("http://3.35.149.202:80/api/rating/find/" + data.movieId, config)
      .then((response) => {
        console.log(response.data);
        if (response != null) {
          setRating(response.data);
          setAverage(response.data[6] / (response.data[0] * 5));
        }
      });
    axios
      .get(
        "http://3.35.149.202:80/api/rating/personal?movieId=" + data.movieId,
        config
      )
      .then((response) => {
        console.log(response);
        if (response != null) {
          setMyRating(response.data);
          setHaveRating(true);
        } else {
          setHaveRating(false);
        }
      });
  };

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
          <>
            <div
              className="fixed top-0 left-0 z-20 w-full h-full bg-black opacity-70"
              onClick={() => {
                setRatingDetailModalOpen(false);
              }}
            ></div>
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
          </>
        )}
        {ratingCreateModalOpen && (
          <>
            <div
              className="fixed top-0 left-0 z-20 w-full h-full bg-black opacity-70"
              onClick={() => {
                setRatingCreateModalOpen(false);
              }}
            ></div>
            <MovieRatingModal
              type="CREATE"
              setRatingCreateModalOpen={setRatingCreateModalOpen}
              movieId={data.movieId}
              movieTitle={data.movieTitle}
              createRatingConfirm={createRatingConfirm}
            />
          </>
        )}
        {ratingEditModalOpen && (
          <>
            <div
              className="fixed top-0 left-0 z-20 w-full h-full bg-black opacity-70"
              onClick={() => {
                setRatingEditModalOpen(false);
              }}
            ></div>
            <MovieRatingModal
              type="EDIT"
              setRatingEditModalOpen={setRatingEditModalOpen}
              movieId={data.movieId}
              movieTitle={data.movieTitle}
              ratingId={myRating[0]}
              story={myRating[1]}
              acting={myRating[2]}
              direction={myRating[3]}
              visual={myRating[4]}
              sound={myRating[5]}
              editRatingConfirm={editRatingConfirm}
            />
          </>
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
