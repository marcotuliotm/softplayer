import api from "./api";

const configure = {
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json;',
    },
  };
  
  
  const URI_API = '/persons';
  
  
  export class PersonsApi {
    static getAll = () => api.get(`${URI_API}`, configure);
    static create = person => api.post(`${URI_API}`, person, configure);
    static remove = id => api.delete(`${URI_API}/${id}`, configure);
    static update = person => api.put(`${URI_API}/${person.id}`, person, configure);
  }
  
  export default PersonsApi;