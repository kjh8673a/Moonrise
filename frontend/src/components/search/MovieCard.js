import axios from 'axios';
import React from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { setMovieDetail, setMovieId } from '../../feature/reducer/MovieReducer';
import { setPartyList } from '../../feature/reducer/PartyReducer';

function MovieCard(props) {
  const movePage = useNavigate();
  const dispatch = useDispatch();
  const imgURL = "https://image.tmdb.org/t/p/w500" + props.poster;
  const baseURL = process.env.REACT_APP_BASE_URL;
  const goCommunity = async ()  => {
    dispatch(setMovieId(props.movieId))
    dispatch(setMovieDetail({poster: imgURL, title: props.title}))
    axios.get(baseURL+ '/api/party/list?movieId=' + props.movieId)
            .then(res => {
              console.log(res);
              dispatch(setPartyList(res.data.data.findParties));
    });
    movePage('/community/list');
  }
  return (
    <div className='relative col-span-1 movieCard' onClick={goCommunity}>
        <img className="object-cover w-full h-full" src={imgURL} alt="포스터 이미지가 없습니다."></img>
        <div className="absolute top-0 left-0 flex flex-col items-center justify-center w-full h-full duration-300 bg-black opacity-0 hover:opacity-90">
          <h1 className="text-2xl text-white">{props.title}</h1>
        </div>
    </div>
  )
}

export default MovieCard