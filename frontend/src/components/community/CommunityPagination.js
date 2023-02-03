import React, { Component } from 'react'

export default class CommunityPagination extends Component {
  render() {
    const numPages = Math.ceil(this.props.total / this.props.limit);
    let page = 1;
    function setPage(i){
        page = i;
    }

    const numButton = Array(numPages).fill().map((arr, i) => (
          <button className="text-white mx-2"
            key={i + 1}
            onClick={() => setPage(i + 1)}
            aria-current={page === i + 1 ? "page" : "none"}
          >
            {i + 1}
          </button>
        ));
    return (
        <div className='flex justify-center mt-2'>
            <button className="text-white mx-2" onClick={() => setPage(page - 1)} disabled={page === 1}>
                &lt;
            </button>
            {numButton}    
            <button className="text-white mx-2" onClick={() => setPage(page + 1)} disabled={page === numPages}>
                &gt;
            </button>
        </div>
    )
  }
}
