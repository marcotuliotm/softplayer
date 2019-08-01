import axios from 'axios';


const api = axios.create({
  baseURL: "http://127.0.0.1:8080/",
});

api.interceptors.request.use((config) => {
  const token = "admin:password";

  const auth = { ...config.auth };

  if (token) {
    auth.username = `admin`;
    auth.password = `password`;
  }

  return { ...config, auth };
});

export default api;
