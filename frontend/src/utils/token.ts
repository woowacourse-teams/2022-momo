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

export { accessTokenProvider };
