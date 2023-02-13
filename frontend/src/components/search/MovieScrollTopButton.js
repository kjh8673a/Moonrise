import React from 'react'

function MovieScrollTopButton(props) {
  return (
    <div className='text-right'>
      <button onClick={props.scrollToTop} className="fixed right-5 bottom-5 text-white bg-[#FA9E13] w-12 h-12 rounded-full font-bold text-xl">TOP</button>
    </div>
  )
}

export default MovieScrollTopButton
