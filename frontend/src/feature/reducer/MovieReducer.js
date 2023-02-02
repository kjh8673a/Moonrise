const SET_MOVIE_LIST = 'MovieReducer/SET_MOVIE_LIST';

export const setMovieList = movieList => ({ type: SET_MOVIE_LIST, movieList });

const initialState = {
  movieList : [],
};

export default function movieReducer(state = initialState, action) {
    switch (action.type) {
      case SET_MOVIE_LIST:
        return {
          ...state,
          movieList: action.movieList
        };
      default:
        return state;
    }
  }