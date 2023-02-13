const SET_ACCESS_TOKEN = "MemberReducer/SET_ACCESS_TOKEN";
const SET_REFRESH_TOKEN = "MemberReducer/SET_REFRESH_TOKEN";
const SET_NICKNAME = "MemberReducer/SET_NICKNAME";
const SET_IS_LOGIN = "MemberReducer/SET_IS_LOGIN";
const LOGOUT_USER = "MemberReducer/LOGOUT_USER";
const GENRES_1 = "MemberReducer/GENRES_1";
const GENRES_2 = "MemberReducer/GENRES_2";
const GENRES_3 = "MemberReducer/GENRES_3";
const IMAGE_PATH = "MemberReducer/IMAGE_PATH";

export const setAccessToken = (accessToken) => ({
  type: SET_ACCESS_TOKEN,
  accessToken,
});
export const setRefreshToken = (refreshToken) => ({
  type: SET_REFRESH_TOKEN,
  refreshToken,
});
export const setNickname = (nickname) => ({ type: SET_NICKNAME, nickname });
export const setGernes1 = (gernes1) => ({ type: GENRES_1, gernes1 });
export const setGernes2 = (gernes2) => ({ type: GENRES_2, gernes2 });
export const setGernes3 = (gernes3) => ({ type: GENRES_3, gernes3 });
export const setImagePath = (imagePath) => ({ type: IMAGE_PATH, imagePath });
export const setIsLogin = (isLogin) => ({ type: SET_IS_LOGIN });
export const logoutUser = () => ({ type: LOGOUT_USER });

const initialState = {
  accessToken: "",
  refreshToken: "",
  nickname: "",
  gernes1: "",
  gernes2: "",
  gernes3: "",
  imagePath: "",
  isLogin: false,
};

export default function MemberReducer(state = initialState, action) {
  switch (action.type) {
    case SET_ACCESS_TOKEN:
      return {
        ...state,
        accessToken: action.accessToken,
      };
    case SET_REFRESH_TOKEN:
      return {
        ...state,
        refreshToken: action.refreshToken,
      };
    case SET_NICKNAME:
      return {
        ...state,
        nickname: action.nickname,
      };
    case GENRES_1:
      return {
        ...state,
        gernes1: action.gernes1,
      };
    case GENRES_2:
      return {
        ...state,
        gernes2: action.gernes2,
      };
    case GENRES_3:
      return {
        ...state,
        gernes3: action.gernes3,
      };
    case IMAGE_PATH:
      return {
        ...state,
        imagePath: action.imagePath,
      };
    case SET_IS_LOGIN:
      return {
        ...state,
        isLogin: true,
      };
    case LOGOUT_USER:
      return {
        accessToken: "",
        refreshToken: "",
        nickname: "",
        isLogin: false,
      };
    default:
      return state;
  }
}
