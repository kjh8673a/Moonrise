const SET_ACCESS_TOKEN = 'MemberReducer/SET_ACCESS_TOKEN';
const SET_REFRESH_TOKEN = 'MemberReducer/SET_REFRESH_TOKEN';
const SET_NICKNAME = 'MemberReducer/SET_NICKNAME';

export const setAccessToken = accessToken => ({ type: SET_ACCESS_TOKEN, accessToken });
export const setRefreshToken = refreshToken => ({ type: SET_REFRESH_TOKEN, refreshToken });
export const setNickname = nickname => ({ type: SET_NICKNAME, nickname });

const initialState = {
    accessToken : "",
    refreshToken : "",
    nickname : "홍길동",
};

export default function movieReducer(state = initialState, action) {
    switch (action.type) {
      case SET_ACCESS_TOKEN:
        return {
          ...state,
          accessToken: action.accessToken
        };
      case SET_REFRESH_TOKEN:
        return {
          ...state,
          refreshToken: action.refreshToken
        };
      case SET_NICKNAME:
        return {
          ...state,
          nickname: action.nickname
        };
      default:
        return state;
    }
  }