import React from 'react'
import { useSelector } from 'react-redux';
import MovieCard from '../components/search/MovieCard';

function Main() {
  
  console.log(useSelector(state => state.movie.movieList))
  const movieList = useSelector(state => state.movie.movieList).map((movie) => (
    <MovieCard poster={movie.poster_path} key={movie.id} title={movie.title} movieId={movie.id}/>
  ));

  return (
    <div className='searchMain bg-community h-full bg-fill p-10 grid grid-cols-5 gap-16'>
      {movieList}
    </div>
  )
}

export default Main
