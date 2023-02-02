const SET_MOVIE_LIST = 'MovieReducer/SET_MOVIE_LIST';
const SET_MOVIE_ID = 'MovieReducer/SET_MOVIE_ID';

export const setMovieList = movieList => ({ type: SET_MOVIE_LIST, movieList });
export const setMovieId = movieId => ({ type: SET_MOVIE_ID, movieId });

const initialState = {
  movieList : [],
  movieId : "",
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
      default:
        return state;
    }
  }