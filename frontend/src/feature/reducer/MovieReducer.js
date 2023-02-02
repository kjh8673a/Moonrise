const SET_MOVIE_LIST = 'MovieReducer/SET_MOVIE_LIST';
const SET_MOVIE_ID = 'MovieReducer/SET_MOVIE_ID';
const SET_MOVIE_DETAIL = 'MovieReducer/SET_MOVIE_DETAIL';

export const setMovieList = movieList => ({ type: SET_MOVIE_LIST, movieList });
export const setMovieId = movieId => ({ type: SET_MOVIE_ID, movieId });
export const setMovieDetail = movieDetail => ({ type: SET_MOVIE_DETAIL, movieDetail });

const initialState = {
  movieList : [],
  movieId : "",
  moviePoster : "",
  movieTitle : "",
};

export default function movieReducer(state = initialState, action) {
    switch (action.type) {
      case SET_MOVIE_LIST:
        return {
          ...state,
          movieList: action.movieList
        };
      case SET_MOVIE_ID:
        return {
          ...state,
          movieId: action.movieId
        };
      case SET_MOVIE_DETAIL:
        return {
          ...state,
          moviePoster: action.movieDetail.poster,
          movieTitle: action.movieDetail.title
        };
      default:
        return state;
    }
  }