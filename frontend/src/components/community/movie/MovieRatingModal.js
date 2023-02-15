import axios from "axios";
import React, { useState } from "react";
import { FaMoon } from "react-icons/fa";
import { useSelector } from "react-redux";
import RatingStar from "../../../feature/UI/RatingStar";

function MovieRatingModal(props) {
  const [story, setStory] = useState();
  const [acting, setActing] = useState();
  const [direction, setDirection] = useState();
  const [visual, setVisual] = useState();
  const [sound, setSound] = useState();

  const access_token = useSelector((state) => state.member.accessToken);
  const config = {
    headers: {
      access_token: access_token,
    },
  };

  const closeModal = () => {
    if (props.type === "DETAIL") {
      props.setRatingDetailModalOpen(false);
    }
    if (props.type === "CREATE") {
      props.setRatingCreateModalOpen(false);
    }
    if (props.type === "EDIT") {
      props.setRatingEditModalOpen(false);
    }
  };

  const createRating = (event) => {
    event.preventDefault();
    axios
      .post(
        "http://3.35.149.202:80/api/rating/create/" + props.movieId,
        {
          movieId: props.movieId,
          story: story,
          acting: acting,
          direction: direction,
          visual: visual,
          sound: sound,
        },
        config
      )
      .then((response) => {
        props.createRatingConfirm();
        closeModal();
      });
  };

  const editRating = (event) => {
    event.preventDefault();
    console.log(props.ratingId)
    axios
      .put(
        "http://3.35.149.202:80/api/rating/update/" + props.ratingId,
        {
          ratingId: props.ratingId,
          movieId: props.movieId,
          story: story,
          acting: acting,
          direction: direction,
          visual: visual,
          sound: sound,
        },
        config
      )
      .then((response) => {
        props.editRatingConfirm();
        closeModal();
      });
  };

  const changeStory = (val) => {
    setStory(val);
  };
  const changeActing = (val) => {
    setActing(val);
  };
  const changeDirection = (val) => {
    setDirection(val);
  };
  const changeVisual = (val) => {
    setVisual(val);
  };
  const changeSound = (val) => {
    setSound(val);
  };
  return (
    <div className="grid grid-rows-5 absolute z-50 bg-[#315B4C] border-2 border-yellow-400 h-100 w-64 top-96 left-96 rounded-lg p-2">
      <button className="absolute text-white right-2" onClick={closeModal}>
        X
      </button>
      <div className="flex items-center row-span-1 px-4 overflow-hidden text-xl font-semibold text-white border-b ">
        <span className="overflow-hidden text-ellipsis whitespace-nowrap">
          {props.movieTitle}
        </span>
      </div>

      <div className="row-span-4 text-white">
        {props.type === "DETAIL" && (
          <div className="grid grid-rows-6 gap-1 px-4 py-2">
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">스토리</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-center"><FaMoon size="20" className="text-[#FA9E13] inline-block"/><b>{props.story}</b></span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연기</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-center"><FaMoon size="20" className="text-[#FA9E13] inline-block"/><b>{props.acting}</b></span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연출</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-center"><FaMoon size="20" className="text-[#FA9E13] inline-block"/><b>{props.direction}</b></span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">영상미</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-center"><FaMoon size="20" className="text-[#FA9E13] inline-block"/><b>{props.visual}</b></span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">사운드</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-center"><FaMoon size="20" className="text-[#FA9E13] inline-block"/><b>{props.sound}</b></span>
            </div>
            <div className="row-span-1"></div>
          </div>
        )}

        {props.type === "CREATE" && (
          <div className="grid grid-rows-6 gap-3 px-4 py-2 ">
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">스토리</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeStory} />
              </div>
              <b className="col-span-1 text-center">{story}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">연기</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeActing} />
              </div>
              <b className="col-span-1 text-center">{acting}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">연출</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeDirection} />
              </div>
              <b className="col-span-1 text-center">{direction}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">영상미</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeVisual} />
              </div>
              <b className="col-span-1 text-center">{visual}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">사운드</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeSound} />
              </div>
              <b className="col-span-1 text-center">{sound}</b>
            </div>
            <div className="row-span-1 text-center">
              <button
                onClick={createRating}
                className="bg-[#FA9E13] px-3 rounded-lg font-semibold w-full"
              >
                평가하기
              </button>
            </div>
          </div>
        )}
        {props.type === "EDIT" && (
          <div className="grid grid-rows-6 gap-1 px-4 py-2">
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">스토리</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeStory} score={props.story} />
              </div>
              <b className="col-span-1 text-center">{story}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">연기</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeActing} score={props.acting} />
              </div>
              <b className="col-span-1 text-center">{acting}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">연출</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar
                  sendScore={changeDirection}
                  score={props.direction}
                />
              </div>
              <b className="col-span-1 text-center">{direction}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">영상미</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeVisual} score={props.visual} />
              </div>
              <b className="col-span-1 text-center">{visual}</b>
            </div>
            <div className="grid grid-cols-4 row-span-1">
              <b className="col-span-1">사운드</b>
              <div className="flex items-center justify-center col-span-2">
                <RatingStar sendScore={changeSound} score={props.sound} />
              </div>
              <b className="col-span-1 text-center">{sound}</b>
            </div>
            <div className="row-span-1 text-center">
              <button
                onClick={editRating}
                className="bg-[#FA9E13] px-3 rounded-lg font-semibold w-full"
              >
                수정하기
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default MovieRatingModal;
