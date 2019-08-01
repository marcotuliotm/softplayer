import axios from 'axios';


const api = axios.create({
  baseURL: process.env.REACT_APP_WEBSITE,
});

api.interceptors.request.use((config) => {
  const token = "admin:password";
  console.log(process.env.REACT_APP_WEBSITE);
  const auth = { ...config.auth };

  if (token) {
    auth.username = `admin`;
    auth.password = `password`;
  }

  return { ...config, auth };
});

export default api;
