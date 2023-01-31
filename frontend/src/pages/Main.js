import React from 'react'
import { useNavigate } from 'react-router-dom'

function Main() {
  const movePage = useNavigate();

  function searchMovie(){
    movePage('search');
  }
  
  return (
    <div>
      메인페이지 입니다
      <button onClick={searchMovie}>검색 결과 보기</button>
    </div>
  )
}

export default Main
