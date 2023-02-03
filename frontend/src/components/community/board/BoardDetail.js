import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import BoardComment from "./BoardComment";

const DUMMY_DATA = [
  {
    board_id: 1,
    movie: "해리포터와 마법사의 돌",
    title: "크리스마스 쯤이면 생각나는 영화",
    content:
      "크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에서 늘 나오는 호그와트의 환상적인 저녁 만찬 장소는 허공을 떠다니는 촛불과 맛있는 음식 그리고 화려한 각 기숙사의 깃발들이 떠오르는데 그 모습들은 어딘가 크리마스를 떠오르게 한다. 조앤 롤링의 소설로 먼저 접했던 영화..  ",
    write_date: "2023.01.17 09:54",
    nickname: "홍길동",
    like_cnt: 34,
    comment_cnt: 14,
    poster:
      "https://images.ctfassets.net/usf1vwtuqyxm/6Meesa1ONupgjIy7JS5TvF/10fff5e089662ef26336c1e2b8414f70/minalima-weasley-family.jpg?w=315&h=315&fit=fill&f=top&fm=webp&q=70",
  },
  {
    board_id: 2,
    movie: "해리포터와 마법사의 돌",
    title: "크리스마스 쯤이면 생각나는 영화",
    content:
      "크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에서 늘 나오는 호그와트의 환상적인 저녁 만찬 장소는 허공을 떠다니는 촛불과 맛있는 음식 그리고 화려한 각 기숙사의 깃발들이 떠오르는데 그 모습들은 어딘가 크리마스를 떠오르게 한다. 조앤 롤링의 소설로 먼저 접했던 영화..  ",
    write_date: "2023.01.17 09:54",
    nickname: "홍길동",
    like_cnt: 34,
    comment_cnt: 14,
    poster:
      "https://images.ctfassets.net/usf1vwtuqyxm/5bqVQEImJpoPAciVRNQqFu/336875e94b38fac41c7c1bed3336dcf6/SHP---Hero-Mob.jpg?w=315&h=315&fit=fill&f=top&fm=webp&q=70",
  },
  {
    board_id: 3,
    movie: "해리포터와 마법사의 돌",
    title: "크리스마스 쯤이면 생각나는 영화",
    content:
      "크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에크리스마스 쯤이면 생각나는 영화들이 몇개 있는데 그 중 하나인 해리포터 시리즈, 해리포터 시리즈에서 늘 나오는 호그와트의 환상적인 저녁 만찬 장소는 허공을 떠다니는 촛불과 맛있는 음식 그리고 화려한 각 기숙사의 깃발들이 떠오르는데 그 모습들은 어딘가 크리마스를 떠오르게 한다. 조앤 롤링의 소설로 먼저 접했던 영화..  ",
    write_date: "2023.01.17 09:54",
    nickname: "홍길동",
    like_cnt: 34,
    comment_cnt: 14,
    poster:
      "https://images.ctfassets.net/usf1vwtuqyxm/5bqVQEImJpoPAciVRNQqFu/336875e94b38fac41c7c1bed3336dcf6/SHP---Hero-Mob.jpg?w=315&h=315&fit=fill&f=top&fm=webp&q=70",
  },
];

function BoardDetail() {
  const [commentValue, setCommentValue] = useState("");

  const movePage = useNavigate();

  const params = new URLSearchParams(window.location.search);
  const id = parseInt(params.get("id"));

  const data = DUMMY_DATA.filter((data) => data.board_id === id);

  const goBack = () => {
    movePage("/community/list/");
  };

  const addComment = (event) => {
    event.preventDefault();

    console.log(commentValue);
  };

  const getValue = (event) => {
    setCommentValue(event.target.value);
  };

  return (
    <div className="w-4/5 min-h-screen p-2 m-auto bg-slate-400">
      {data.map((board) => (
        <>
          <div className="flex flex-col items-center border-b">
            <span className="text-[#FA9E13] font-semibold">{board.movie}</span>
            <span className="text-2xl font-extrabold">{board.title}</span>
            <div className="flex w-full">
              <span className="flex-1 cursor-pointer" onClick={goBack}>
                &lt; 이전으로
              </span>
              <span className="flex-1 text-center">{board.write_date}</span>
              <span className="flex-1"></span>
            </div>
          </div>
          <div className="p-2 border-b">
            <p>{board.content}</p>
          </div>
        </>
      ))}

      <span>댓글</span>
      <div className="p-2 border-b-2 border-black bg-slate-300">
        <form className="flex gap-2" onSubmit={addComment}>
          <input
            type="text"
            className="flex-1 rounded p-2"
            placeholder="내용을 입력해 주세요"
            onChange={getValue}
          ></input>
          <button className="w-20 h-20 bg-[#FA9E13] rounded text-white">
            등록
          </button>
        </form>
      </div>

      <BoardComment board_id={id} />
    </div>
  );
}

export default BoardDetail;
