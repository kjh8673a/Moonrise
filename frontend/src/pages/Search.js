import React from 'react'
import { useSelector } from 'react-redux';
import MainNav from '../components/common/MainNav';
import MovieCard from '../components/search/MovieCard';

function Main() {
  
  const movieList = useSelector(state => state.movie.movieList).map((movie) => (
    <MovieCard movie={movie} key={movie.id}/>
  ));

  return (
    <div className='grid h-full px-10 pb-10 searchMain bg-community bg-fill'>
      <MainNav/>
      <div className="grid grid-cols-5 gap-16">
        {movieList}
      </div>
    </div>
  )
}

export default Main
