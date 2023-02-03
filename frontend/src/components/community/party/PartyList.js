import React, { useEffect, useState } from 'react'

import PartyCard from './PartyCard'
import CommunityHeader from '../CommunityHeader'
import axios from 'axios';
import { useSelector } from 'react-redux';

const GetList = () => {
  const [parties, setParties] = useState([]);
  const movieId = useSelector(state => state.movie.movieId);

  useEffect(() => {
      axios.get('http://3.35.149.202:80/api/party/list?movieId=' + movieId)
          .then(response => {
              setParties(response.data.findParties);
          });
  });
  const partyList = parties.map((party) => (
    <PartyCard title={party.title} partyDate="2023.02.06" partyPeople={party.partyPeople} partyLocation={party.location} partyId={party.partyId} key={party.partyId}/>
  ));
  return (
    partyList
  );
}

function PartyList() {
  // const partyList = partyInfo.map((pi) => (
  //   <PartyCard title={pi.title} partyDate={pi.partyDate} partyPeople={pi.partyPeople} partyLocation={pi.partyLocation} key={pi.id} />
  // ));
  return (
    <div className='party-list'>
      <CommunityHeader type="뒷풀이"/>
      <div className="grid grid-cols-4 gap-2">
        {GetList()}
      </div>
    </div>
  )
}

export default PartyList