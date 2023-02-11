import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import ProfileSeeMoreButton from "../ProfileSeeMoreButton";
import ProfilePartyCard from "./ProfilePartyCard";

function ProfilePartyParticipateList() {
  const [data, setData] = useState([]);
  const [limit, setLimit] = useState(8);
  const [list, setList] = useState([]);

  const access_token = useSelector((state) => state.member.accessToken);

  useEffect(() => {
    const config = {
      headers: {
        access_token: access_token,
      },
    };
    axios
      .get("http://3.35.149.202:80/api/party/join/list", config)
      .then((response) => {
        setData(response.data.data.myPartyJoinList);
      });
  }, [access_token]);

  useEffect(() => {
    if(data.length > 0) {
      setList(data.filter((item, index) => index < limit));
    }
  }, [data, limit]);

  const seeMore = () => {
    setLimit(limit + 8);
  };

  return (
    <div className="p-2">
      <ul className="grid grid-cols-4 gap-4 justify-items-center">
        {list.map((party) => (
          <ProfilePartyCard
            moim_id={party.moim_id}
            title={party.title}
            movie={party.movie}
            moim_date={party.moim_date}
            status={party.status}
            online={party.online}
            location={party.location}
            image={party.image}
          />
        ))}
      </ul>
      {limit < data.length && <ProfileSeeMoreButton seeMore={seeMore} />}
    </div>
  );
}

export default ProfilePartyParticipateList;
