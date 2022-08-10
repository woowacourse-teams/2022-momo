import Axios from 'axios';

const baseURL = process.env.BASE_URL;

const axios = Axios.create({
  baseURL,
});

export { baseURL };
export default axios;
