import React from "react";

const DUMMY_DATA = {
  movie_id: 1,
  title: "해리포터와 마법사의 돌",
  poster:
    "https://w.namu.la/s/c9ba02a6e315d754e406990be1612035d7c92f763f184c84d299e4245d63d6e8d6189cc85c46c70ec185c72cbf740b4a2e4e5528406108d02f3e5c23947525d08d1d6ae1a899adc80f1d4ad655660142b4180e581029cf8be97ced7da302637647d40ad01864b43e374fd3839ab33491",
  director: ["크리스 콜럼버스"],
  actor: ["다니엘 래드클리프", "엠마 왓슨", "루퍼트 그린트"],
  gerne: ["판타지", "가족", "모험", "액션"],
  rating: [4.8, 3.5, 4.0, 4.3, 3.9],
};

function MovieDetail() {
  const data = DUMMY_DATA;
  const average =
    DUMMY_DATA.rating.reduce((a, c) => a + c) / DUMMY_DATA.rating.length;
  return (
    <div className="float-left w-1/5 bg-emerald-600">
      <ul>
        <li>
          <img className="w-2/3 m-auto" src={data.poster} alt="poster" />
        </li>
        <li className="text-2xl font-bold">{data.title}</li>
        <li>{average} 평점상세 평가하기</li>
        <li className="overflow-hidden text-ellipsis whitespace-nowrap">
          <b>감독</b> {data.director.join()}
        </li>
        <li className="overflow-hidden text-ellipsis whitespace-nowrap">
          <b>주연</b> {data.actor.join()}
        </li>
        <li className="overflow-hidden text-ellipsis whitespace-nowrap">
          <b>장르</b> {data.gerne.join()}
        </li>
      </ul>
    </div>
  );
}

export default MovieDetail;
