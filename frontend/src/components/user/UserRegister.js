import React from 'react'

const getButtons = () => {
    const genreList = ["코미디", "SF", "멜로", "액션", "범죄", "스릴러", "전쟁", "판타지", "스포츠"];
  
      const buttonList = genreList.map((genre) => (
        <div className="col-span-1 py-4 text-lg text-center text-white border-2 border-white rounded-lg">
            {genre}
        </div>
      ))
      
    return (
        buttonList
    );
  }

function UserRegister() {
   
  return (
    <div className='grid content-center h-full grid-cols-3 bg-black UserRegister bg-opacity-60'>
        <div className='col-span-3 text-center'>
            <p className='m-4 text-4xl text-white'>추가 정보 입력</p>
            <div className='grid grid-cols-3'>
                <div className="col-span-1 col-start-2 bg-black rounded-lg bg-opacity-60">
                    <div className='m-10 text-left'>
                        <p className='text-gray-300'>닉네임</p>
                        <input type="text" id="title" className="block py-2.5 px-0 w-full text-sm text-gray-300 bg-transparent border-0 border-b-2 border-gray-300 appearance-none  focus:outline-none focus:ring-0 focus:border-orange-300 peer" placeholder="" />
                    </div>
                    <div className='m-10 text-left'>
                        <p className='text-gray-300'>선호 장르</p>
                        <div className='grid grid-cols-3 gap-3 mt-4'>
                            {getButtons()}
                        </div>
                    </div>
                    <button className='w-5/6 h-10 mb-5 bg-orange-400 rounded-lg'>회원가입</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default UserRegister