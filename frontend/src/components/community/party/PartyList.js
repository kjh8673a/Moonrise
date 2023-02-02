import React, { useEffect, useState } from 'react'

import PartyCard from './PartyCard'
import CommunityHeader from '../CommunityHeader'
import axios from 'axios';

const GetList = () => {
  const [parties, setParties] = useState([]);
  useEffect(() => {
      axios.get('http://i8b310.p.ssafy.io:9001/party/list?movieId=257211')
          .then(response => {
              setParties(response.data.findParties);
          });
  }, []);
  console.log(parties);
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