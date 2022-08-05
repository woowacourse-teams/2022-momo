import Axios from 'axios';

const baseURL = 'https://api.moyeora.site/api';

const axios = Axios.create({
  baseURL,
});

export { baseURL };
export default axios;
