const accessTokenProvider = {
  get: () => {
    return sessionStorage.getItem('accessToken') ?? '';
  },
  set: (accessToken: string) => {
    sessionStorage.setItem('accessToken', accessToken);
  },
  remove: () => {
    sessionStorage.removeItem('accessToken');
  },
};

const refreshTokenProvider = {
  get: () => {
    return sessionStorage.getItem('refreshToken') ?? '';
  },
  set: (refreshToken: string) => {
    sessionStorage.setItem('refreshToken', refreshToken);
  },
  remove: () => {
    sessionStorage.removeItem('refreshToken');
  },
};

export { accessTokenProvider, refreshTokenProvider };
