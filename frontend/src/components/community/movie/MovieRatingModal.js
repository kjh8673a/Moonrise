import axios from "axios";
import React, { useState } from "react";
import { useSelector } from "react-redux";

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
        console.log("평점 등록!")
        closeModal();
      });
  };

  const editRating = (event) => {
    event.preventDefault();
    axios
    .post(
      "http://3.35.149.202:80/api/rating/update/" + props.movieId,
      {
        ratingId: props.ratingId,
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
      console.log("평점 등록!")
      closeModal();
    });
  }

  const getStoryValue = (event) => {
    setStory(event.target.value);
  };
  const getActingValue = (event) => {
    setActing(event.target.value);
  };
  const getDirectionValue = (event) => {
    setDirection(event.target.value);
  };
  const getVisualValue = (event) => {
    setVisual(event.target.value);
  };
  const getSoundValue = (event) => {
    setSound(event.target.value);
  };

  return (
    <div className="grid grid-rows-5 absolute z-50 bg-[#315B4C] border-2 border-yellow-400 h-60 w-60 top-96 left-96 rounded-lg p-2">
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
              <span className="col-span-1 text-right">{props.story}</span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연기</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-right">{props.acting}</span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연출</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-right">{props.direction}</span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">영상미</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-right">{props.visual}</span>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">사운드</b>{" "}
              <span className="col-span-1"></span>{" "}
              <span className="col-span-1 text-right">{props.sound}</span>
            </div>
            <div className="row-span-1"></div>
          </div>
        )}

        {props.type === "CREATE" && (
          <div className="grid grid-rows-6 gap-1 px-4 py-2">
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">스토리</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                onChange={getStoryValue}
                value={story}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연기</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                onChange={getActingValue}
                value={acting}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연출</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                onChange={getDirectionValue}
                value={direction}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">영상미</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                onChange={getVisualValue}
                value={visual}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">사운드</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                onChange={getSoundValue}
                value={sound}
              ></input>
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
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">스토리</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                defaultValue={props.story}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연기</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                defaultValue={props.acting}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">연출</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                defaultValue={props.direction}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">영상미</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                defaultValue={props.visual}
              ></input>
            </div>
            <div className="grid grid-cols-3 row-span-1">
              <b className="col-span-1">사운드</b>{" "}
              <input
                type="number"
                className="col-span-2 px-2 text-black"
                defaultValue={props.sound}
              ></input>
            </div>
            <div className="row-span-1 text-center">
              <button onClick={editRating} className="bg-[#FA9E13] px-3 rounded-lg font-semibold w-full">
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
