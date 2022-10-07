const accessTokenProvider = {
  get: () => {
    return localStorage.getItem('accessToken') ?? '';
  },
  set: (accessToken: string) => {
    localStorage.setItem('accessToken', accessToken);
  },
  remove: () => {
    localStorage.removeItem('accessToken');
  },
};

const refreshTokenProvider = {
  get: () => {
    return localStorage.getItem('refreshToken') ?? '';
  },
  set: (refreshToken: string) => {
    localStorage.setItem('refreshToken', refreshToken);
  },
  remove: () => {
    localStorage.removeItem('refreshToken');
  },
};

export { accessTokenProvider, refreshTokenProvider };
