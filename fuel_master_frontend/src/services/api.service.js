import axios from "axios";

const API_BASE_URL = "https://api-fuel-master-fbc37438737d.herokuapp.com/api/v1";
// const API_BASE_URL = 'http://localhost:8080/api/v1';

const apiService = {
  init() {
    axios.defaults.baseURL = API_BASE_URL;
    axios.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem("token");
        if (token) {
          if (config.url === "/v1/user/login" && token !== undefined) {
            config.headers.Authorization = `Bearer ${token}`;
          } else {
            config.headers.Authorization = `Bearer ${token}`;
          }
        }
        config.headers["Content-Type"] = "application/json";
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );
  },

  get(resource) {
    return axios.get(API_BASE_URL + resource);
  },

  post(resource, data) {
    return axios.post(API_BASE_URL + resource, data);
  },

  put(resource, data) {
    return axios.put(API_BASE_URL + resource, data);
  },

  delete(resource) {
    return axios.delete(API_BASE_URL + resource);
  },
};

export default apiService;
