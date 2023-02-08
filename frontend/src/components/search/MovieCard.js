import axios from 'axios';
import React from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { setMovieDetail, setMovieId } from '../../feature/reducer/MovieReducer';
import { setPartyList } from '../../feature/reducer/PartyReducer';

function MovieCard(props) {
  const movePage = useNavigate();
  const dispatch = useDispatch();
  const imgURL = "https://image.tmdb.org/t/p/w500" + props.movie.poster_path;
  const baseURL = process.env.REACT_APP_BASE_URL;
  const movieData = {
      genre: props.movie.genre_ids,
      id: props.movie.id,
      originalTitle: props.movie.original_title,
      popularity: props.movie.popularity,
      release_date: props.movie.release_date,
      title: props.movie.title
  }
  const goCommunity = async ()  => {
    dispatch(setMovieId(props.movie.id))
    dispatch(setMovieDetail({poster: imgURL, title: props.movie.title}))
    console.log(movieData)
    axios.post(baseURL + '/api/movie',movieData).then(res => {console.log(res)})
    axios.get(baseURL+ '/api/party/list?movieId=' + props.movie.id)
            .then(res => {
              dispatch(setPartyList(res.data.data.findParties));
    });
    movePage('/community/list/party');
  }
  return (
    <div className='relative col-span-1 movieCard' onClick={goCommunity}>
        <img className="object-cover w-full h-full" src={imgURL} alt="포스터 이미지가 없습니다."></img>
        <div className="absolute top-0 left-0 flex flex-col items-center justify-center w-full h-full duration-300 bg-black opacity-0 hover:opacity-90">
          <h1 className="text-2xl text-white">{props.movie.title}</h1>
        </div>
    </div>
  )
}

export default MovieCard