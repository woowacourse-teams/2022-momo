import Axios from 'axios';

export const baseURL = 'http://localhost:8080/api';

const axios = Axios.create({
  baseURL,
});

export default axios;
