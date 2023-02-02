import axios from 'axios';
import React, { useState } from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { setMovieList } from '../../feature/reducer/MovieReducer';

function MainSearch() {
  const movePage = useNavigate();
  const [keyword, setKeyword] = useState("");
  
  function goMovieList(){
    movePage('/search/');
  }

  
  const keywordHandler = (event) => {
    setKeyword(event.target.value);
  }
  
  const dispatch = useDispatch(); 

  const searchMovies = async () => {
    axios.get('https://api.themoviedb.org/3/search/movie?api_key=ceb258a90f2367a7fb92cb7d4d6763da&language=ko-KR&page=1&include_adult=false&query='+keyword)
        .then(response => {
            dispatch(setMovieList(response.data.results))
        })
        .then(() => {
            if (keyword === "") {
                console.log("검색어 입력 필요");
            }
            else{
                goMovieList()
            }
        });
  }

  return (
    <div className='mainSearch'>
      <div className="grid grid-cols-2 gap-4 my-16">
        <div className="col-span-1 text-right">
            <p className='text-orange-600 text-7xl'>달:-뜨다</p>
        </div>
        <div className="col-span-1 mt-6">
            <p className="text-white">동사 [마음이]</p>
            <p className="text-white"> 가라앉지 않고 들썽거리다.</p>
        </div>
      </div>
      <div className='my-6 text-center'>
        <p className='text-white text-2xl'>영화보고 달뜬 마음, <span className='text-orange-600 text-3xl'>달뜸</span>으로 가져오세요.</p>
      </div>
      <div className="h-16 mx-16 flex">
        <input type="text" onChange={keywordHandler} id="party-search" className="block h-16 w-5/6 pl-4 text-lg border border-gray-400 rounded-lg focus:ring-white focus:border-white" placeholder="어떤 영화를 보고 오셨나요?"/>
        <button onClick={searchMovies} className='w-1/6 text-center text-white hover:bg-green-600'>검색</button>
      </div>
      
    </div>
  )
}

export default MainSearch