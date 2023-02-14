import { Tab } from '@headlessui/react';
import React from 'react'
import { useNavigate } from 'react-router-dom';

function CommunityNav() {
  const movePage = useNavigate();
  const categories = [
    {title: "게시글", func : changeBoard},
    {title: "담소", func : changeTalk},
    {title: "뒷풀이", func : changeParty},
  ];
  function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
  }

  function changeBoard(){
    movePage('/community/list/');
  }
  function changeTalk(){
    movePage('/community/list/talk');
  }
  function changeParty(){
    movePage('/community/list/party');
  }
  
  return (
    <div className='relative mb-4'>
      <Tab.Group>
        <Tab.List className="flex p-1 space-x-1 bg-transparent border-b-2 border-white/20">
          {categories.map((category, index) => (
            <Tab
              key={index}
              onClick={category.func}
              className={({ selected }) =>
                classNames(
                  'w-full py-2.5 leading-5 rounded-lg text-gray-400',
                  'focus:outline-none',
                  selected ? 'text-dal-orange text-lg' : 'hover:bg-white/5 hover:text-white'
                )
              }
            >
              {category.title}
            </Tab>
          ))}
        </Tab.List>
      </Tab.Group>
    </div>
  )
}

export default CommunityNav