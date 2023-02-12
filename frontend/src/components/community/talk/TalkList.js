import axios from "axios";
import {Transition} from '@headlessui/react'
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";

import CommunityHeader from "../CommunityHeader";
import CommunityPagination from "../CommunityPagination";
import TalkCard from "./TalkCard";


function TalkList() {
  const movieId = useSelector(state => state.movie.movieId)
  const [talkList, setTalkList] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const baseURL = process.env.REACT_APP_BASE_URL;
  
  const [page, setPage] = useState(0);
  useEffect(() => {
    axios.get(baseURL + "/api/debate/list?movieId="+movieId+"&page="+page)
    .then(response => {
      setTalkList(response.data.data.findParties)
      setTotalPages(response.data.data.totalPages)
      console.log(response.data);
    });
    return () => {
    }
  }, [baseURL, movieId, page])
  
  return (
    <div>
      <CommunityHeader type="담소" />
      <Transition
        appear={true} 
        show={true}
       enter="transition-all duration-1000"
       enterFrom="opacity-0 transform translate-x-10"
       enterTo="opacity-100 transform translate-x-0"
      >
        <div className="grid grid-cols-2 gap-4">
          {talkList.map((talk, idx) => (
            <TalkCard talkInfo={talk} key={idx}/>
          ))}
        </div>
      <CommunityPagination total={totalPages} page={page} setPage={setPage}/>
      </Transition>
    </div>
  );
}

export default TalkList;
