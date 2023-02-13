import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import Profile from "../../../assets/img/profile.png";
import {
  setGernes1,
  setGernes2,
  setGernes3,
  setNickname,
} from "../../../feature/reducer/MemberReducer";

function ProfileEditorCard(props) {
  const [nicknameValue, setNicknameValue] = useState(
    useSelector((state) => state.member.nickname)
  );
  const [gerneValue, setGerneValue] = useState([]);
  const [hideOpt, setHideOpt] = useState(true);
  const [gerneError, setGerneError] = useState(false);
  const [imgPreview, setImgPreview] = useState("");
  const imgRef = useRef();
  const dispatch = useDispatch();

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
    "판타지",
  ];

  useEffect(() => {
    let copy = [];
    if (props.gerne1) {
      copy[0] = props.gerne1[0];
    }
    if (props.gerne2) {
      copy[1] = props.gerne2[0];
    }
    if (props.gerne3) {
      copy[2] = props.gerne3[0];
    }
    setGerneValue([...new Set(copy)]);
  }, [props]);

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
    console.log(reader)
    reader.readAsDataURL(file);
    reader.onload = () => {
      setImgPreview(reader.result);
    };
  };

  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };

  const editProfile = (event) => {
    event.preventDefault();
    console.log(gerneValue);
    axios
      .put(
        "http://3.35.149.202:80/auth/member/",
        {
          nickname: nicknameValue,
          imagePath:
            "https://moonrise.s3.ap-northeast-2.amazonaws.com/208c18ba-3457-419e-b27a-a408ceb60e8b_defaultUser.png",
          genres: gerneValue,
        },
        config
      )
      .then((response) => {
        dispatch(setNickname(response.data.data.nickname));
        if (response.data.data.genres) {
          dispatch(setGernes1(response.data.data.genres[0]));
          dispatch(setGernes2(response.data.data.genres[1]));
          dispatch(setGernes3(response.data.data.genres[2]));
        }
        props.editDone();
        props.closeEditor();
      });
  };

  return (
    <div className="grid h-full grid-rows-6 p-5">
      <div className="grid grid-cols-4 row-span-5 border-b">
        <div className="col-span-1 mx-auto">
          {hideOpt && props.imagePath && !imgPreview && (
            <img
              className="w-32 h-32 rounded-full border-1"
              src={props.imagePath}
              alt=""
              onMouseEnter={() => {
                setHideOpt(false);
              }}
            />
          )}
          {hideOpt && !props.imagePath && !imgPreview && (
            <img
              className="w-32 h-32 rounded-full border-1"
              src={Profile}
              alt=""
              onMouseEnter={() => {
                setHideOpt(false);
              }}
            />
          )}
          {hideOpt && imgPreview && (
            <img
              className="w-32 h-32 rounded-full border-1"
              src={imgPreview}
              alt=""
              onMouseEnter={() => {
                setHideOpt(false);
              }}
            />
          )}

          {!hideOpt && (
            <div
              className="flex items-center justify-center w-32 h-32 bg-black rounded-full cursor-pointer border-1"
              onMouseLeave={() => {
                setHideOpt(true);
              }}
            >
              <label className="font-bold text-white" htmlFor="file">
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
        <div className="relative grid col-span-3 grid-rows-6">
          <div className="absolute top-0 right-0">
            <button className="font-semibold" onClick={props.closeEditor}>
              X
            </button>
          </div>
          <div className="row-span-5 px-5">
            <div className="mb-1">
              <p className="text-xl font-bold">닉네임</p>
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
                <span className="text-xl font-bold ">선호 장르</span>
                {gerneValue.map((gerne) => (
                  <>
                    {gerne && (
                      <span className="bg-[#B3B6B7] ml-3 pl-3 pr-1 rounded-xl text-sm">
                        {gerne}
                        <button onClick={() => gerneDeletehandeler({ gerne })}>
                          X
                        </button>
                      </span>
                    )}
                  </>
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
                    {gerneValue.includes({ gerne }.gerne) && (
                      <button
                        className="py-1 text-white bg-gray-500 border-2 rounded-md"
                        onClick={() => gerneDeletehandeler({ gerne })}
                      >
                        {gerne}
                      </button>
                    )}
                    {!gerneValue.includes({ gerne }.gerne) && (
                      <button
                        className="py-1 border-2 rounded-md"
                        onClick={() => gerneChangeHandeler({ gerne })}
                      >
                        {gerne}
                      </button>
                    )}
                  </>
                ))}
              </div>
            </div>
          </div>
          <div className="flex items-center justify-center row-span-1 text-center">
            <button
              className="bg-[#FA9E13] p-2 text-white font-semibold rounded-md"
              onClick={editProfile}
            >
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
