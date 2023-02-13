import React from 'react'

function MovieSeeMoreButton(props) {
  return (
    <div className="mt-4 text-center">
      <button className="text-xl text-white" onClick={props.seeMore}>
        더보기
      </button>
    </div>
  )
}

export default MovieSeeMoreButton
