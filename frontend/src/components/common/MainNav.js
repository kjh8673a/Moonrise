import axios from "axios";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setMovieList } from "../../feature/reducer/MovieReducer";
import Logo from "./Logo";
import ProfileIcon from "./ProfileIcon";

function MainNav() {
  const movePage = useNavigate();
  const [keyword, setKeyword] = useState("");

  function goMovieList() {
    movePage("/search/");
  }

  const keywordHandler = (event) => {
    setKeyword(event.target.value);
  };

  const handleOnKeyPress = (e) => {
    if (e.key === "Enter") {
      searchMovies(); // Enter 입력이 되면 클릭 이벤트 실행
    }
  };

  const dispatch = useDispatch();

  const tmdbToken = process.env.REACT_APP_TMDB_TOKEN;

  const searchMovies = async () => {
    axios
      .get(
        "https://api.themoviedb.org/3/search/movie?api_key=" +
          tmdbToken +
          "&language=ko-KR&page=1&include_adult=false&query=" +
          keyword
      )
      .then((response) => {
        dispatch(setMovieList(response.data.results));
      })
      .then(() => {
        if (keyword === "") {
          console.log("검색어 입력 필요");
        } else {
          goMovieList();
        }
      });
  };
  return (
    <div className="grid w-full grid-cols-3 my-8 main-nav">
      <Logo />
      <div className="relative col-span-1">
        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
          <svg
            aria-hidden="true"
            className="w-5 h-5 text-gray-500 dark:text-gray-400"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
            ></path>
          </svg>
        </div>
        <input
          type="text"
          id="party-search"
          onChange={keywordHandler}
          onKeyPress={handleOnKeyPress}
          className="block w-full h-10 pl-10 text-sm text-white bg-transparent border border-gray-400 rounded-full focus:ring-white focus:border-white dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
          placeholder="다른 영화도 검색해보세요!"
        />
      </div>
      <ProfileIcon />
    </div>
  );
}

export default MainNav;
