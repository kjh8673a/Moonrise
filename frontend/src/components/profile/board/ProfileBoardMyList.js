import React, { useEffect, useState } from "react";
import ProfileSeeMoreButton from "../ProfileSeeMoreButton";
import ProfileBoardCard from "./ProfileBoardCard";

const DUMMY_DATA = [
  {
    board_id: 1,
    title: "말포이 저만 귀엽나요?",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.02.03 19:54",
    image:
      "https://hips.hearstapps.com/cosmouk.cdnds.net/15/08/nrm_1424419881-draco-malfoy-harry-potter.jpg",
  },
  {
    board_id: 2,
    title: "해리포터 vs 론",
    movie: "해리포터와 비밀의 방",
    write_date: "2023.01.23 20:32",
    image:
      "https://media1.popsugar-assets.com/files/thumbor/V9b7dVAHo85zeFNKjQI50lt5Bpc/180x0:1151x971/fit-in/1024x1024/filters:format_auto-!!-:strip_icc-!!-/2020/03/09/822/n/1922283/d035b45e5e668eb1bdb724.73813433_/i/Ron-Weasley-Pictures-From-Harry-Potter-Movies.jpg",
  },
  {
    board_id: 3,
    title: "헤르미온느 팬 양성소",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.12.23 12:54",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    board_id: 4,
    title: "가장 인상 깊었던 장면은..",
    movie: "인턴",
    write_date: "2022.01.22 11:51",
    image:
      "https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg",
  },
  {
    board_id: 5,
    title: "호그와트 초상화로 살기 vs 부엌 지박령 집요정으로 살기",
    movie: "해리포터와 아즈카반의 죄수",
    write_date: "2022.12.18 22:12",
    image: "https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg",
  },
  {
    board_id: 6,
    title: "해리포터가 돌을 지키지 못했다면?",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.12.23 09:54",
    image:
      "https://play-lh.googleusercontent.com/2kBabvPwoBWnnSFWYyjXuKaK5hrmRwA662aOJ5LaVvJv8F2O8BAvrv7DbpOxuWfz2w11",
  },
  {
    board_id: 7,
    title: "해리포터가 돌을 지키지 못했다면?",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.12.23 09:54",
    image:
      "https://play-lh.googleusercontent.com/2kBabvPwoBWnnSFWYyjXuKaK5hrmRwA662aOJ5LaVvJv8F2O8BAvrv7DbpOxuWfz2w11",
  },
  {
    board_id: 8,
    title: "말포이 저만 귀엽나요?",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.02.03 19:54",
    image:
      "https://hips.hearstapps.com/cosmouk.cdnds.net/15/08/nrm_1424419881-draco-malfoy-harry-potter.jpg",
  },
  {
    board_id: 9,
    title: "해리포터 vs 론",
    movie: "해리포터와 비밀의 방",
    write_date: "2023.01.23 20:32",
    image:
      "https://media1.popsugar-assets.com/files/thumbor/V9b7dVAHo85zeFNKjQI50lt5Bpc/180x0:1151x971/fit-in/1024x1024/filters:format_auto-!!-:strip_icc-!!-/2020/03/09/822/n/1922283/d035b45e5e668eb1bdb724.73813433_/i/Ron-Weasley-Pictures-From-Harry-Potter-Movies.jpg",
  },
  {
    board_id: 10,
    title: "헤르미온느 팬 양성소",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.12.23 12:54",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    board_id: 11,
    title: "가장 인상 깊었던 장면은..",
    movie: "인턴",
    write_date: "2022.01.22 11:51",
    image:
      "https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg",
  },
  {
    board_id: 12,
    title: "호그와트 초상화로 살기 vs 부엌 지박령 집요정으로 살기",
    movie: "해리포터와 아즈카반의 죄수",
    write_date: "2022.12.18 22:12",
    image: "https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg",
  },
  {
    board_id: 13,
    title: "해리포터가 돌을 지키지 못했다면?",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.12.23 09:54",
    image:
      "https://play-lh.googleusercontent.com/2kBabvPwoBWnnSFWYyjXuKaK5hrmRwA662aOJ5LaVvJv8F2O8BAvrv7DbpOxuWfz2w11",
  },
  {
    board_id: 14,
    title: "해리포터가 돌을 지키지 못했다면?",
    movie: "해리포터와 마법사의 돌",
    write_date: "2022.12.23 09:54",
    image:
      "https://play-lh.googleusercontent.com/2kBabvPwoBWnnSFWYyjXuKaK5hrmRwA662aOJ5LaVvJv8F2O8BAvrv7DbpOxuWfz2w11",
  },
];

function ProfileBoardMyList() {
  const data = DUMMY_DATA;
  const [limit, setLimit] = useState(8);
  const [list, setList] = useState([]);

  useEffect(() => {
    setList(data.filter((item, index) => index < limit));
  }, [data, limit]);

  const seeMore = () => {
    setLimit(limit + 8);
  };

  return (
    <div className="p-2">
      <ul className="grid grid-cols-4 gap-4 justify-items-center">
        {list.map((board) => (
          <ProfileBoardCard
            board_id={board.board_id}
            title={board.title}
            movie={board.movie}
            write_date={board.write_date}
            image={board.image}
          />
        ))}
      </ul>
      {limit < data.length && <ProfileSeeMoreButton seeMore={seeMore} />}
    </div>
  );
}

export default ProfileBoardMyList;
