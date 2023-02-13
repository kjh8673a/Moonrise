const SET_MOVIE_LIST = "MovieReducer/SET_MOVIE_LIST";
const SET_MOVIE_ID = "MovieReducer/SET_MOVIE_ID";
const SET_MOVIE_DETAIL = "MovieReducer/SET_MOVIE_DETAIL";
const SET_TOTAL_PAGE = "MovieReducer/SET_TOTAL_PAGE";
const SET_SEARCH_KEYWORD = "MovieReducer/SET_SEARCH_KEYWORD";
const SET_CURRENT_PAGE = "MovieReducer/SET_CURRENT_PAGE";

export const setMovieList = (movieList) => ({
  type: SET_MOVIE_LIST,
  movieList,
});
export const setMovieId = (movieId) => ({ type: SET_MOVIE_ID, movieId });
export const setMovieDetail = (movieDetail) => ({
  type: SET_MOVIE_DETAIL,
  movieDetail,
});
export const setTotalPage = (totalPage) => ({
  type: SET_TOTAL_PAGE,
  totalPage,
});
export const setSearchKeyword = (searchKeyword) => ({
  type: SET_SEARCH_KEYWORD,
  searchKeyword,
});
export const setCurrentPage = (currentPage) => ({
  type: SET_CURRENT_PAGE,
  currentPage,
});

const initialState = {
  movieList: [],
  movieId: "",
  moviePoster: "",
  movieTitle: "",
  totalPage: 0,
  searchKeyword: "",
  currentPage: 0,
};

export default function movieReducer(state = initialState, action) {
  switch (action.type) {
    case SET_MOVIE_LIST:
      return {
        ...state,
        movieList: action.movieList,
      };
    case SET_MOVIE_ID:
      return {
        ...state,
        movieId: action.movieId,
      };
    case SET_MOVIE_DETAIL:
      return {
        ...state,
        moviePoster: action.movieDetail.poster,
        movieTitle: action.movieDetail.title,
      };
    case SET_TOTAL_PAGE:
      return {
        ...state,
        totalPage: action.totalPage,
      };
    case SET_SEARCH_KEYWORD:
      return {
        ...state,
        searchKeyword: action.searchKeyword,
      };
    case SET_CURRENT_PAGE:
      return {
        ...state,
        currentPage: action.currentPage,
      };
    default:
      return state;
  }
}
