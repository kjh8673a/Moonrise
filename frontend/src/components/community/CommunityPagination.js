import React from "react";

function CommunityPagination(props) {
  const numPages = props.total;

  const numButton = Array(numPages)
    .fill()
    .map((arr, i) => (
      <button
        className="mx-2 text-white"
        key={i + 1}
        onClick={() => props.setPage(i)}
        aria-current={props.page === i + 1 ? "page" : "none"}
        disabled={props.page === i}
      >
        {i + 1}
      </button>
    ));

    const numButtonBoard = Array(numPages)
    .fill()
    .map((arr, i) => (
      <button
        className="mx-2 text-white"
        key={i + 1}
        onClick={() => props.setPage(i*2)}
        aria-current={props.page === i*2 ? "page" : "none"}
        disabled={props.page === i}
      >
        {i + 1}
      </button>
    ));
  return (
    <div className="flex justify-center mt-4 pagination">
      {props.type === "BOARD" && (
        <>
          <button
            className="mx-2 text-white"
            onClick={() => props.setPage(props.page - 2)}
            disabled={props.page === 0}
          >
            &lt;
          </button>
          {numButtonBoard}
          <button
            className="mx-2 text-white"
            onClick={() => props.setPage(props.page + 2)}
            disabled={props.page + 2 > numPages}
          >
            &gt;
          </button>
        </>
      )}
      {props.type !== "BOARD" && (
        <>
          <button
            className="mx-2 text-white"
            onClick={() => props.setPage(props.page - 1)}
            disabled={props.page === 0}
          >
            &lt;
          </button>
          {numButton}
          <button
            className="mx-2 text-white"
            onClick={() => props.setPage(props.page + 1)}
            disabled={props.page + 1 === numPages}
          >
            &gt;
          </button>
        </>
      )}
    </div>
  );
}

export default CommunityPagination;
