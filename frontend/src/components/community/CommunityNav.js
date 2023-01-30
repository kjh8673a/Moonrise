
import React, { Component } from 'react'

export default class CommunityNav extends Component {
  render() {
    return (
      <div className='mb-4'>
        <ul class="navbar-nav flex border-b pb-2">
            <li class="flex-1 text-center">
                <a class="nav-link text-white hover:text-white" href="/board">게시글</a>
            </li>
            <li class="flex-1 text-center">
                <a class="text-gray-400 hover:text-white" href="/">담소</a>
            </li>
            <li class="flex-1 text-center">
                <a class="text-gray-400 hover:text-white" href="/party">뒷풀이</a>
            </li>
        </ul>
      </div>
    )
  }
}
