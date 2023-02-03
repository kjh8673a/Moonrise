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
              dispatch(setPartyList(res.data.findParties));
    });
    movePage('/community/list');
  }
  return (
    <div className='col-span-1' onClick={goCommunity}>
        <img className="z-0 w-full h-full" src={imgURL} alt="포스터 이미지가 없습니다."></img>
    </div>
  )
}

export default MovieCard