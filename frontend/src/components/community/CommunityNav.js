
import React, { Component } from 'react'

export default class CommunityNav extends Component {
  render() {
    return (
      <div class="w-3/5 bg-black">
        <ul class="navbar-nav flex border-b">
            <li class="flex-1 text-center mr-6">
                <a class="nav-link text-white hover:text-blue-800 text-lg" href="/">게시글</a>
            </li>
            <li class="flex-1 text-center mr-6">
                <a class="text-gray-400 hover:text-white" href="/">담소</a>
            </li>
            <li class="flex-1 text-center mr-6">
                <a class="text-gray-400 hover:text-white" href="/party">뒷풀이</a>
            </li>
        </ul>
      </div>
    )
  }
}
