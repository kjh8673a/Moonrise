const SET_ACCESS_TOKEN = 'MemberReducer/SET_ACCESS_TOKEN';
const SET_REFRESH_TOKEN = 'MemberReducer/SET_REFRESH_TOKEN';
const SET_NICKNAME = 'MemberReducer/SET_NICKNAME';
const SET_IS_LOGIN = 'MemberReducer/SET_IS_LOGIN';
const LOGOUT_USER = 'MemberReducer/LOGOUT_USER'

export const setAccessToken = accessToken => ({ type: SET_ACCESS_TOKEN, accessToken });
export const setRefreshToken = refreshToken => ({ type: SET_REFRESH_TOKEN, refreshToken });
export const setNickname = nickname => ({ type: SET_NICKNAME, nickname });
export const setIsLogin = isLogin => ({ type: SET_IS_LOGIN });
export const logoutUser = () => ({ type: LOGOUT_USER });

const initialState = {
    accessToken : "",
    refreshToken : "",
    nickname : "",
    isLogin : false,
};

export default function MemberReducer(state = initialState, action) {
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
      case SET_IS_LOGIN:
        return {
          ...state,
          isLogin : true
        }
      case LOGOUT_USER:
        return{
          accessToken : "",
          refreshToken : "",
          nickname : "",
          isLogin : false,
        }
      default:
        return state;
    }
  }