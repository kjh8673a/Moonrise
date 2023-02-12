import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { FaMoon } from "react-icons/fa";
import MovieRatingModal from "./MovieRatingModal";

function MovieDetail() {
  const [rating, setRating] = useState([0, 0, 0, 0, 0, 0, 0]);
  const [myRating, setMyRating] = useState([0, 0, 0, 0, 0, 0]);
  const [average, setAverage] = useState(0);
  const [genres, setGenres] = useState([]);
  const [overview, setOverview] = useState("");
  const [haveRating, setHaveRating] = useState(false);
  const [ratingDetailModalOpen, setRatingDetailModalOpen] = useState(false);
  const [ratingCreateModalOpen, setRatingCreateModalOpen] = useState(false);
  const [ratingEditModalOpen, setRatingEditModalOpen] = useState(false);
  const [noneMessage, setNoneMessage] = useState("");

  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };

  const data = useSelector((state) => state.movie);

  const tmdbToken = process.env.REACT_APP_TMDB_TOKEN;
  useEffect(() => {
    axios
      .get(
        "https://api.themoviedb.org/3/movie/" +
          data.movieId +
          "?api_key=" +
          tmdbToken +
          "&language=ko-KR"
      )
      .then((response) => {
        setGenres(response.data.genres);
        setOverview(response.data.overview);
      });
  }, [data.movieId, tmdbToken]);

  useEffect(() => {
    const config = {
      headers: {
        access_token: access_token,
      },
    };
    axios
      .get("http://3.35.149.202:80/api/rating/find/" + data.movieId, config)
      .then((response) => {
        if (response.data[0] !== 0) {
          let ratings = [];
          for(let i = 0; i < 5; i++) {
            ratings[i] = (response.data[i + 1] / response.data[0]).toFixed(2);
          }
          setRating(ratings);
          setAverage((response.data[6] / (response.data[0] * 5)).toFixed(2));
          setNoneMessage("");
        } else {
          setNoneMessage("평점을 등록해주세요!");
        }
      });
    axios
      .get(
        "http://3.35.149.202:80/api/rating/personal?movieId=" + data.movieId,
        config
      )
      .then((response) => {
        if (response.data !== "") {
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
        if (response.data[0] !== 0) {
          let ratings = [];
          for(let i = 0; i < 5; i++) {
            ratings[i] = (response.data[i + 1] / response.data[0]).toFixed(2);
          }
          setRating(ratings);
          setAverage((response.data[6] / (response.data[0] * 5)).toFixed(2));
          setNoneMessage("");
        } else {
          setNoneMessage("평점을 등록해주세요!");
        }
      });
    axios
      .get(
        "http://3.35.149.202:80/api/rating/personal?movieId=" + data.movieId,
        config
      )
      .then((response) => {
        if (response !== "") {
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
          {!noneMessage && <><FaMoon size="30" className="text-[#FA9E13] inline-block"/><b className="text-lg">{average}</b></>}
          {noneMessage && (
            <>
              <span className="text-[#FA9E13] font-semibold">
                {noneMessage}
              </span>
            </>
          )}
          <button onClick={showRatingDetailModal} className="ml-4 hover:text-[#FA9E13] hover:font-bold">평점상세</button>
          {!haveRating && (
            <button onClick={showRatingCreateModal} className="ml-2 hover:text-[#FA9E13] hover:font-bold">평가하기</button>
          )}
          {haveRating && (
            <button onClick={showRatingEditModal} className="ml-2 hover:text-[#FA9E13] hover:font-bold">평가수정</button>
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
          <b className="text-lg text-white">장르</b>
          {genres.map((genre)=> (
            <span className="ml-2">{genre.name}</span>
          ))}
        </p>
        <p className="text-gray-300">
          <b className="text-lg text-white">줄거리</b>
          <div className="pr-10">{overview}</div>
        </p>
      </ul>
    </div>
  );
}

export default MovieDetail;
