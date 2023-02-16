import React, { useRef, useState } from "react";

function ProfileEditorCard(props) {
  const [nicknameValue, setNicknameValue] = useState("남극도둑갈매기");
  const [gerneValue, setGerneValue] = useState(["액션", "드라마", "SF"]);
  const [hideOpt, setHideOpt] = useState(true);
  const [gerneError, setGerneError] = useState(false);
  const [imgPreview, setImgPreview] = useState(
    "http://ojsfile.ohmynews.com/AT_T_IMG/2018/0420/A0002426619_T.jpg"
  );
  const imgRef = useRef();

  const gerneList = [
    "SF",
    "가족",
    "공포",
    "다큐멘터리",
    "드라마",
    "로맨스",
    "모험",
    "미스터리",
    "범죄",
    "서부",
    "스릴러",
    "애니메이션",
    "액션",
    "역사",
    "음악",
    "전쟁",
    "코미디",
    "판타지"
  ];

  const nicknameChangeHandler = (event) => {
    let { value } = { ...event.target };
    setNicknameValue(value);
  };

  const gerneDeletehandeler = (props) => {
    setGerneValue(
      gerneValue.filter((gerneValue) => gerneValue !== props.gerne)
    );
  };

  const gerneChangeHandeler = (props) => {
    if (gerneValue.length >= 3) {
      setGerneError(true);
    } else {
      let copy = [...gerneValue];
      copy[gerneValue.length] = props.gerne;
      setGerneValue([...new Set(copy)]);
      setGerneError(false);
    }
  };

  const changeImage = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      setImgPreview(reader.result);
    };
  };

  return (
    <div className="grid h-full grid-rows-6 p-5">
      <div className="row-span-5 border-b grid grid-cols-4">
        <div className="mx-auto col-span-1">
          {hideOpt && (
            <img
              className="rounded-full h-32 w-32 border-1"
              src={imgPreview}
              alt=""
              onMouseEnter={() => {
                setHideOpt(false);
              }}
            />
          )}

          {!hideOpt && (
            <div
              className="rounded-full h-32 w-32 border-1 bg-black flex items-center justify-center cursor-pointer"
              onMouseLeave={() => {
                setHideOpt(true);
              }}
            >
              <label className="text-white font-bold" htmlFor="file">
                사진 변경
              </label>
              <input
                id="file"
                type="file"
                accept="image/*"
                onChange={changeImage}
                ref={imgRef}
                className="hidden"
              />
            </div>
          )}
        </div>
        <div className="col-span-3 grid grid-rows-6 relative">
          <div className="absolute right-0 top-0">
            <button className="font-semibold" onClick={props.closeEditor}>
              X
            </button>
          </div>
          <div className="row-span-5 px-5">
            <div className="mb-1">
              <p className="font-bold text-xl">닉네임</p>
              <p className="text-xs text-gray-400">
                사이트에서 사용할 닉네임을 입력해주세요
              </p>
              <input
                type="text"
                id="nickname"
                onChange={nicknameChangeHandler}
                value={nicknameValue}
                placeholder=""
                className="w-full p-2 border-2 rounded-md"
              ></input>
              <p className="text-sm">닉네임은 2~6자 입니다</p>
            </div>
            <div>
              <div>
                <span className="font-bold text-xl ">선호 장르</span>
                {gerneValue.map((gerne) => (
                  <span className="bg-[#B3B6B7] ml-3 pl-3 pr-1 rounded-xl text-sm">
                    {gerne}
                    <button onClick={() => gerneDeletehandeler({ gerne })}>
                      X
                    </button>
                  </span>
                ))}
              </div>
              {!gerneError && (
                <p className="text-xs text-gray-400">
                  선호 장르를 선택해주세요 (최대 3개)
                </p>
              )}
              {gerneError && (
                <p className="text-xs text-red-600">
                  선호 장르를 선택해주세요 (최대 3개)
                </p>
              )}
              <div className="grid grid-cols-4 gap-1 mt-1">
                {gerneList.map((gerne) => (
                  <>
                    {gerneValue.includes({gerne}.gerne) && (
                      <button
                        className="border-2 py-1 rounded-md bg-gray-500 text-white"
                        onClick={() => gerneDeletehandeler({gerne})}
                      >
                        {gerne}
                      </button>
                    )}
                    {!gerneValue.includes({gerne}.gerne) && (
                      <button
                        className="border-2 py-1 rounded-md"
                        onClick={() => gerneChangeHandeler({gerne})}
                      >
                        {gerne}
                      </button>
                    )}
                  </>
                ))}

              </div>
            </div>
          </div>
          <div className="row-span-1 text-center flex items-center justify-center">
            <button className="bg-[#FA9E13] p-2 text-white font-semibold rounded-md">
              회원 정보 수정
            </button>
          </div>
        </div>
      </div>
      <div className="flex items-center justify-center row-span-1">
        <div className="text-xl font-bold ">달:뜸</div>
      </div>
    </div>
  );
}

export default ProfileEditorCard;
