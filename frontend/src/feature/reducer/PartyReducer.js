const SET_PARTY_ID = 'PartyReducer/SET_PARTY_ID';
const SET_PARTY_DETAIL = 'PartyReducer/SET_PARTY_DETAIL';

export const setPartyId = partyId => ({ type: SET_PARTY_ID, partyId });
export const setPartyDetail = partyDetail => ({ type: SET_PARTY_DETAIL, partyDetail });

const initialState = {
  partyId: 0,
  partyDetail: {},
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
      default:
        return state;
    }
  }