import axios from "axios";
import React from "react";
import { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import MainNav from "../components/common/MainNav";
import MovieCard from "../components/search/MovieCard";
import MovieScrollTopButton from "../components/search/MovieScrollTopButton";
import MovieSeeMoreButton from "../components/search/MovieSeeMoreButton";
import { setCurrentPage, setMovieList } from "../feature/reducer/MovieReducer";
import { animated, useSpring } from "@react-spring/web";

function Main() {
  const dispatch = useDispatch();
  const scrollRef = useRef();

  const movieList = useSelector((state) => state.movie.movieList);
  const totalPage = useSelector((state) => state.movie.totalPage);
  const searchKeyword = useSelector((state) => state.movie.searchKeyword);
  const currentPage = useSelector((state) => state.movie.currentPage);

  const tmdbToken = process.env.REACT_APP_TMDB_TOKEN;

  const style = useSpring({
    from: { opacity: "0" },
    to: { opacity: "1" },
    config: { duration: "1500" },
  });

  const seeMore = () => {
    axios
      .get(
        "https://api.themoviedb.org/3/search/movie?api_key=" +
          tmdbToken +
          "&language=ko-KR&page=1&include_adult=false&query=" +
          searchKeyword +
          "&page=" +
          (currentPage + 1)
      )
      .then((response) => {
        const copy = [
          ...movieList,
          ...response.data.results.filter(
            (movie) => movie.poster_path !== null
          ),
        ];
        dispatch(setMovieList(copy));
        dispatch(setCurrentPage(currentPage + 1));
      });
  };

  const scrollToTop = () => {
    scrollRef.current.scrollIntoView({ behavior: "smooth", block: "start" });
  };

  return (
    <div
      ref={scrollRef}
      className="grid h-full px-10 pb-10 searchMain bg-community bg-fill"
    >
      <MainNav />
      <div className="grid grid-cols-5 gap-16">
        {movieList.map((movie) => (
          <animated.div style={style}>
            <MovieCard movie={movie} key={movie.id} />
          </animated.div>
        ))}
      </div>
      {currentPage < totalPage && <MovieSeeMoreButton seeMore={seeMore} />}
      {currentPage > 1 && <MovieScrollTopButton scrollToTop={scrollToTop} />}
    </div>
  );
}

export default Main;
