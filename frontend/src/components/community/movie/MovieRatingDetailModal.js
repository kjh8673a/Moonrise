import React from 'react'

function MovieRatingDetailModal(props) {
    const closeModal = () => {
        props.setRatingDetailModalOpen(false);
    }

  return (
    <div className='grid grid-rows-5 absolute z-50 bg-[#315B4C] border-2 border-yellow-400 h-60 w-60 top-96 left-96 rounded-lg p-2'>
      <button className='absolute text-white right-2' onClick={closeModal}>X</button>
      <div className='flex items-center row-span-1 text-xl font-semibold text-white border-b'>{props.movieTitle}</div>
      <div className='row-span-4'>모달창!</div>
    </div>
  )
}

export default MovieRatingDetailModal
