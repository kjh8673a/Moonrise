import React from 'react'
import MainSearch from '../components/main/MainSearch';

function Main() {
  return (
    <div className='main grid grid-cols-3 pb-0 mb-0 bg-main bg-cover'>
      <div className="relative col-span-1 bg-white h-screen"></div>
      <div className="fixed z-60 top-40 left-64 bg-green-800 h-96 w-2/5">
        <MainSearch/>
      </div>
    </div>
  )
}

export default Main
