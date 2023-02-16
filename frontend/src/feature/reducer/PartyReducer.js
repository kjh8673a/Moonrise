const SET_PARTY_ID = 'PartyReducer/SET_PARTY_ID';
const SET_PARTY_DETAIL = 'PartyReducer/SET_PARTY_DETAIL';
const SET_PARTY_LIST = 'PartyReducer/SET_PARTY_LIST';

export const setPartyId = partyId => ({ type: SET_PARTY_ID, partyId });
export const setPartyDetail = partyDetail => ({ type: SET_PARTY_DETAIL, partyDetail });
export const setPartyList = partyList => ({ type: SET_PARTY_LIST, partyList });

const initialState = {
  partyId: 0,
  partyDetail: {},
  partyList: [],
};

export default function partyReducer(state = initialState, action) {
    switch (action.type) {
      case SET_PARTY_ID:
        return {
          ...state,
          partyId: action.partyId
        };
      case SET_PARTY_DETAIL:
        return {
          ...state,
          partyDetail: action.partyDetail
        };
      case SET_PARTY_LIST:
        return {
          ...state,
          partyList: action.partyList
        };
      default:
        return state;
    }
  }