import React, { Component } from 'react'

import PartyCard from './PartyCard'
import CommunityHeader from '../CommunityHeader'

const partyInfo =[
  {
    'id' : 1,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
  {
    'id' : 2,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
  {
    'id' : 3,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
  {
    'id' : 4,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
  {
    'id' : 5,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
  {
    'id' : 6,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
  {
    'id' : 7,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
  {
    'id' : 8,
    'title' : '같이 헤르미온느 덕질 해요',
    'partyDate' : '2023.02.01',
    'partyPeople' : '2',
    'partyLocation' : '서울시 종로구'
  },
]

export default class PartyList extends Component {
  render() {
    const partyList = partyInfo.map((pi) => (
      <PartyCard title={pi.title} partyDate={pi.partyDate} partyPeople={pi.partyPeople} partyLocation={pi.partyLocation} key={pi.id} />
    ));
    return (
      <div>
        <CommunityHeader type="뒷풀이"/>
        <div class="grid grid-cols-4 gap-2">
          {partyList}
        </div>
      </div>
    )
  }
}
