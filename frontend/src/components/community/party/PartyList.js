import React, { useEffect, useState } from 'react'

import PartyCard from './PartyCard'
import CommunityHeader from '../CommunityHeader'
import { useSelector } from 'react-redux';
import axios from 'axios';
import CommunityPagination from '../CommunityPagination'
import { Transition } from '@headlessui/react';

function PartyList() {
  const movieId = useSelector(state => state.movie.movieId)
  const [partyList, setPartyList] = useState([]);
  const [page, setPage] = useState(0);
  const [partyTotalPages, setPartyTotalPages] = useState(0);
  const baseURL = process.env.REACT_APP_BASE_URL;
  
  const GetList = () => {
    return (
      partyList.map((party) => (
        <PartyCard title={party.title} partyDate={party.partyDate} partyPeople={party.partyPeople} partyLocation={party.location} partyId={party.partyId} key={party.partyId}/>
      ))
    );
  }
  useEffect(() => {
    axios.get(baseURL+ '/api/party/list?movieId=' + movieId +'&page='+ page)
      .then(res => {
        setPartyList(res.data.data.findParties);
        setPartyTotalPages(res.data.data.totalPages);
    });  
    return () => {
    }
  }, [baseURL, page, movieId])
  return (
    <div className='party-list'>
      <CommunityHeader type="뒷풀이"/>
      <Transition
        appear={true} 
        show={true}
       enter="transition-all duration-1000"
       enterFrom="opacity-0 transform translate-x-10"
       enterTo="opacity-100 transform translate-x-0"
      >
      <div className="grid grid-cols-4 gap-2">
        {GetList()}
      </div>
      <CommunityPagination total={partyTotalPages} page={page} setPage={setPage}/>
      </Transition>
    </div>
  )
}

export default PartyList