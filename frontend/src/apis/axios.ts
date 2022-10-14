import Axios from 'axios';

import { accessTokenProvider, refreshTokenProvider } from 'utils/token';

const baseURL = process.env.BASE_URL;

const axios = Axios.create({
  baseURL,
});

const axiosWithAccessToken = Axios.create({
  baseURL,
});

axiosWithAccessToken.interceptors.request.use(
  config => {
    const accessToken = accessTokenProvider.get();

    if (config.headers && accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  },
);

const axiosWithRefreshToken = Axios.create({
  baseURL,
});

axiosWithRefreshToken.interceptors.request.use(
  config => {
    const refreshToken = refreshTokenProvider.get();

    if (config.headers && refreshToken) {
      config.headers.Authorization = `Bearer ${refreshToken}`;
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  },
);

export { baseURL, axios, axiosWithAccessToken, axiosWithRefreshToken };
