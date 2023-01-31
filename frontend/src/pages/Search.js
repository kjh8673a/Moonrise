import React from 'react'
import { useNavigate } from 'react-router-dom'

function Main() {
  const movePage = useNavigate();
  
  function goCommunity(){
    movePage('/community');
  }

  return (
    <div>
      검색 결과 페이지
      <button onClick={goCommunity}>커뮤니티 이동</button>
    </div>
  )
}

export default Main
