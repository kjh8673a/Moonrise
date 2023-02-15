import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { FaMoon } from "react-icons/fa";
import MovieRatingModal from "./MovieRatingModal";
import { useNavigate } from "react-router";

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
  const [isHovering, setIsHovering] = useState(false);
  const [isBigImage, setIsBigImage] = useState(false);
  const isLogin = useSelector(state=> state.member.isLogin);
  const movePage = useNavigate();

  const isHovered = "w-full opacity-30";
  const isNotHovered = "w-full";

  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };

  const data = useSelector((state) => state.movie);
  const movieImg = useSelector((state) => state.movie.moviePoster);

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
          for (let i = 0; i < 5; i++) {
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
    if (isLogin) {
      setRatingCreateModalOpen(true);
    } else {
      movePage("/user/");
    }
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
          for (let i = 0; i < 5; i++) {
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

  const mouseEnterHandler = () => {
    setIsHovering(true);
  };

  const mouseLeaveHandler = () => {
    setIsHovering(false);
  };

  const expandImage = () => {
    setIsBigImage(!isBigImage);
    setIsHovering(false);
  };

  return (
    <div className="my-5 MovieDetail ">
      <ul>
        <li className="mb-3 text-3xl font-bold text-white">
          {useSelector((state) => state.movie.movieTitle)}
        </li>
        {!isBigImage && (
          <>
            <li className="mb-2">
              <div className="grid grid-cols-2 ">
                <div
                  className="col-span-1 relative "
                  onMouseEnter={mouseEnterHandler}
                  onMouseLeave={mouseLeaveHandler}
                >
                  <img
                    className={isHovering ? isHovered : isNotHovered}
                    src={movieImg}
                    alt="poster"
                  />
                  {isHovering && (
                    <div
                      onClick={expandImage}
                      className="absolute top-0 left-0 flex items-center justify-center w-full h-full cursor-pointer"
                    >
                      <span className="text-white font-extrabold text-xl">
                        크게보기
                      </span>
                    </div>
                  )}
                </div>

                <div className="col-span-1 grid items-end">
                  <div className="text-sm text-white pl-5 pb-2 w-full">
                    <div className="mb-1">
                      {!noneMessage && (
                        <>
                          <FaMoon
                            size="30"
                            className="text-[#FA9E13] inline-block"
                          />
                          <b className="text-lg">{average}</b>
                        </>
                      )}
                      {noneMessage && (
                        <>
                          <span className="text-[#FA9E13] font-semibold">
                            {noneMessage}
                          </span>
                        </>
                      )}
                    </div>
                    <div>
                      {!noneMessage && (
                        <button
                          onClick={showRatingDetailModal}
                          className="hover:text-[#FA9E13] hover:font-bold mr-2"
                        >
                          평점보기
                        </button>
                      )}
                      {!haveRating && (
                        <button
                          onClick={showRatingCreateModal}
                          className="hover:text-[#FA9E13] hover:font-bold"
                        >
                          평가하기
                        </button>
                      )}
                      {haveRating && (
                        <button
                          onClick={showRatingEditModal}
                          className="hover:text-[#FA9E13] hover:font-bold"
                        >
                          평가수정
                        </button>
                      )}
                    </div>
                    <div className="overflow-auto text-gray-300 mt-5 w-4/5">
                      <b className="text-lg text-white">장르</b>
                      <br />
                      {genres.map((genre) => (
                        <span className="mr-2">{genre.name}</span>
                      ))}
                    </div>
                  </div>
                </div>
              </div>
            </li>

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

            <p className="text-gray-300">
              <b className="text-lg text-white">줄거리</b>
              <div className="pr-10">{overview}</div>
            </p>
          </>
        )}
        {isBigImage && (
          <li>
            <div
              className="relative w-11/12"
              onMouseEnter={mouseEnterHandler}
              onMouseLeave={mouseLeaveHandler}
            >
              <img
                className={isHovering ? isHovered : isNotHovered}
                src={movieImg}
                alt="poster"
              />
              {isHovering && (
                <div
                  className="absolute top-0 left-0 flex items-center justify-center w-full h-full cursor-pointer"
                  onClick={expandImage}
                >
                  <span className="text-white font-extrabold text-xl">
                    작게보기
                  </span>
                </div>
              )}
            </div>
          </li>
        )}
      </ul>
    </div>
  );
}

export default MovieDetail;
