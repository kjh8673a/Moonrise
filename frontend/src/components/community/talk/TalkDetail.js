import React from "react";

const DUMMY_DATA = [
  {
    discuss_id: 1,
    title: "해리포터가 돌을 지키지 못했다면?",
    content:
      " 해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드해리포터 시리즈의 기점 ‘해리포터와 마법사의 돌‘. 해리포터의 결말을 새드엔딩으로 바꾸어 볼까요?",
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

function TalkDetail() {
  const params = new URLSearchParams(window.location.search);
  const id = parseInt(params.get("id"));

  const data = DUMMY_DATA.filter((data) => data.discuss_id === id);

  return (
    <div className="grid min-h-screen grid-rows-[100px_450px_50px] bg-[#315B4C]">
      {data.map((talk) => (
        <>
          {/* 제목이랑 버튼 수직정렬 */}
          <div className="flex flex-col p-2 text-white border-b"> 
            <b>{talk.title}</b>
            <span>{talk.write_date}</span>
          </div>
          <div className="p-4 overflow-scroll text-white border-b">
            <p>{talk.content}</p>
          </div>
          <div className="flex p-2 m-auto">
            <button className="w-40 h-10 bg-[#FA9E13] rounded text-white"><b>입장하기</b></button>
          </div>
        </>
      ))}
    </div>
  );
}

export default TalkDetail;
