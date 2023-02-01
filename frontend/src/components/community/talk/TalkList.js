import React from "react";

import CommunityHeader from "../CommunityHeader";
import TalkCard from "./TalkCard";

const DUMMY_DATA = [
  {
    discuss_id: 1,
    title: "해리포터가 돌을 지키지 못했다면?",
    content:
      " 해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드엔딩으로 바꾸어 볼까요?",
    write_date: "2022.10.18",
    nikcname: "홍길동",
    latest: "1분 전",
  },
  {
    discuss_id: 2,
    title: "해리포터 vs 론",
    content:
      " 해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드엔딩으로 바꾸어 볼까요?",
    write_date: "2022.10.18",
    nikcname: "홍길동",
    latest: "11분 전",
  },
  {
    discuss_id: 3,
    title: "가장 인상 깊었던 장면은..",
    content:
      " 해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드엔딩으로 바꾸어 볼까요?",
    write_date: "2022.10.18",
    nikcname: "홍길동",
    latest: "15분 전",
  },
  {
    discuss_id: 4,
    title: "말포이 저만 귀엽나요?",
    content:
      " 해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드엔딩으로 바꾸어 볼까요?",
    write_date: "2022.10.18",
    nikcname: "홍길동",
    latest: "17분 전",
  },
  {
    discuss_id: 5,
    title: "호그와트 초상화로 살기 vs 부엌 지박령 집요정으로 살기",
    content:
      " 해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드엔딩으로 바꾸어 볼까요?",
    write_date: "2022.10.18",
    nikcname: "홍길동",
    latest: "21분 전",
  },
  {
    discuss_id: 6,
    title: "헤르미온느 팬 양성소",
    content:
      " 해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드엔딩으로 바꾸어 볼까요?",
    write_date: "2022.10.18",
    nikcname: "홍길동",
    latest: "40분 전",
  },
];

function TalkList() {
  const data = DUMMY_DATA;

  return (
    <div>
      <CommunityHeader type="담소" />
      <ul className="grid grid-cols-2 gap-4">
        {data.map((talk) => (
          <TalkCard 
            key={talk.discuss_id}
            id={talk.discuss_id}
            title={talk.title}
            content={talk.content}
            write_date={talk.write_date}
            nikcname={talk.nikcname}
            latest={talk.latest}
          />
        ))}
      </ul>
    </div>
  );
}

export default TalkList;
