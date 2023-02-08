import React from 'react'

import PartyCard from './PartyCard'
import CommunityHeader from '../CommunityHeader'
import { useSelector } from 'react-redux';

const GetList = () => {

  const parties = useSelector(state => state.party.partyList); 

  const partyList = parties.map((party) => (
    <PartyCard title={party.title} partyDate="2023.02.06" partyPeople={party.partyPeople} partyLocation={party.location} partyId={party.partyId} key={party.partyId}/>
  ));
  return (
    partyList
  );
}

function PartyList() {
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