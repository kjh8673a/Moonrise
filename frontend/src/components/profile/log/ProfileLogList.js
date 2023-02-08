import React, { useEffect, useState } from "react";
import ProfileSeeMoreButton from "../ProfileSeeMoreButton";
import ProfileLogCard from "./ProfileLogCard";

const DUMMY_DATA = [
  {
    id: 1,
    title: "해리포터가 돌을 지키지 못했다면?",
    type: "TALK",
    image:
      "https://play-lh.googleusercontent.com/2kBabvPwoBWnnSFWYyjXuKaK5hrmRwA662aOJ5LaVvJv8F2O8BAvrv7DbpOxuWfz2w11",
  },
  {
    id: 2,
    title: "해리포터 vs 론",
    type: "TALK",
    image:
      "https://media1.popsugar-assets.com/files/thumbor/V9b7dVAHo85zeFNKjQI50lt5Bpc/180x0:1151x971/fit-in/1024x1024/filters:format_auto-!!-:strip_icc-!!-/2020/03/09/822/n/1922283/d035b45e5e668eb1bdb724.73813433_/i/Ron-Weasley-Pictures-From-Harry-Potter-Movies.jpg",
  },
  {
    id: 3,
    title: "가장 인상 깊었던 장면은..",
    type: "BOARD",
    image:
      "https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg",
  },
  {
    id: 4,
    title: "말포이 저만 귀엽나요?",
    type: "PARTY",
    image:
      "https://hips.hearstapps.com/cosmouk.cdnds.net/15/08/nrm_1424419881-draco-malfoy-harry-potter.jpg",
  },
  {
    id: 5,
    title: "호그와트 초상화로 살기 vs 부엌 지박령 집요정으로 살기",
    type: "TALK",
    image:
      "https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg",
  },
  {
    id: 6,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 7,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 8,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 9,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 10,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 11,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 12,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 13,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 14,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 15,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 16,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 17,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 18,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
  {
    id: 19,
    title: "헤르미온느 팬 양성소",
    type: "PARTY",
    image: "https://i.insider.com/60772a1742061500181757bc?width=700",
  },
];

function ProfileLogList() {
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
        {list.map((log) => (
          <ProfileLogCard
            id={log.id}
            title={log.title}
            type={log.type}
            image={log.image}
          />
        ))}
      </ul>
      {limit < data.length && <ProfileSeeMoreButton seeMore={seeMore} />}
    </div>
  );
}

export default ProfileLogList;
