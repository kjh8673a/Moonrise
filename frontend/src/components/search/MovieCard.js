import React from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { setMovieDetail, setMovieId } from '../../feature/reducer/MovieReducer';

function MovieCard(props) {
  const movePage = useNavigate();
  const dispatch = useDispatch();
  const imgURL = "https://image.tmdb.org/t/p/w500" + props.poster;

  function goCommunity(){
    dispatch(setMovieId(props.movieId))
    dispatch(setMovieDetail({poster: imgURL, title: props.title}))
    movePage('/community/list');
  }
  return (
    <div className='col-span-1' onClick={goCommunity}>
        <img className="w-full h-full z-0" src={imgURL} alt="포스터 이미지가 없습니다."></img>
    </div>
  )
}

export default MovieCard