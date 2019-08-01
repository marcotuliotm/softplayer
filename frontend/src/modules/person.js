import PersonsApi from "../services/personsApi";

export const LOAD_PERSONS_LOADING = 'LOAD_PERSONS_LOADING';
export const LOAD_PERSONS_SUCCESS = 'LOAD_PERSONS_SUCCESS';
export const PERSONS_ERROR = 'LOAD_PERSONS_ERROR';
export const CREATE_PERSON_LOADING = 'CREATE_PERSON_LOADING';
export const CREATE_PERSON_SUCCESS = 'CREATE_PERSON_SUCCESS';
export const EDIT_PERSON_LOADING = 'EDIT_PERSON_LOADING';
export const EDIT_PERSON_SUCCESS = 'EDIT_PERSON_SUCCESS';
export const DELETE_PERSON_LOADING = 'DELETE_PERSON_LOADING';
export const DELETE_PERSON_SUCCESS = 'DELETE_PERSON_SUCCESS';
export const CANCEL = 'CANCEL';

export const loadAll = () => dispatch => {
    dispatch({ type: LOAD_PERSONS_LOADING });

    PersonsApi.getAll()
        .then(response => response.data)
        .then(
            data => dispatch({ type: LOAD_PERSONS_SUCCESS, data }),
            error => dispatch({ type: PERSONS_ERROR, error: error.response.data || 'Unexpected Error!!!' })
        )
};

export const create = (person) => dispatch => {
    dispatch({ type: CREATE_PERSON_LOADING });

    PersonsApi.create(person)
        .then(response => response.data)
        .then(
            data => dispatch({ type: CREATE_PERSON_SUCCESS, data }),
            error => dispatch({ type: PERSONS_ERROR, error: error.response.data || 'Unexpected Error!!!' })
        )
        
};

export const remove = (person) => dispatch => {
    dispatch({ type: DELETE_PERSON_LOADING });

    PersonsApi.remove(person.id)
        .then(response => response.data)
        .then(
            data => dispatch({ type: DELETE_PERSON_SUCCESS, person }),
            error => dispatch({ type: PERSONS_ERROR, error: error.response.data || 'Unexpected Error!!!' })
        )
};

export const edit = (person) => dispatch => {
    dispatch({ type: EDIT_PERSON_LOADING });

    PersonsApi.update(person)
        .then(response => response.data)
        .then(
            data => dispatch({ type: EDIT_PERSON_SUCCESS, data }),
            error => dispatch({ type: PERSONS_ERROR, error: error.response.data || 'Unexpected Error!!!' })
        )
};

const initialState = {
    data: [],
    loading: false,
    error: []
};

export const cancel = () => dispatch => {
    dispatch({ type: CANCEL });
}

const person = (state = {}, action) => {
    switch (action.type) {
      case EDIT_PERSON_SUCCESS:
        if (state.id !== action.data.id) {
          return state;
        }
        return action.data;
      default:
        return state;
    }
  };

export default function persons(state = initialState, action) {
    switch (action.type) {
        case DELETE_PERSON_LOADING:
        case CREATE_PERSON_LOADING:
        case LOAD_PERSONS_LOADING: {
            return {
                ...state,
                loading: true,
                error: []
            };
        }
        case LOAD_PERSONS_SUCCESS: {
            return {
                ...state,
                data: action.data,
                loading: false
            }
        }
        case PERSONS_ERROR: {
            return {
                ...state,
                loading: false,
                error: action.error
            };
        }
        case EDIT_PERSON_SUCCESS:
            return {
                ...state,
                data: state.data.map(p => person(p, action)),
                loading: false,
                error: action.error
            }
        case CREATE_PERSON_SUCCESS: {
            return {
                ...state,
                data: [...state.data, action.data],
                loading: false,
                error: []
            }
        }
        case DELETE_PERSON_SUCCESS: {
            return {
                ...state,
                data: state.data.filter(person => person !== action.person),
                loading: false,
                error: []
            }
        }
        case CANCEL: {
            return {
                ...state,
                error: []
            };
        }
        default: {
            return state;
        }
    }
}