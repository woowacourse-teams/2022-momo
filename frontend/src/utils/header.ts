import { accessTokenProvider } from './token';

const authenticationHeader = () =>
  accessTokenProvider.get() === ''
    ? {}
    : {
        headers: {
          Authorization: `Bearer ${accessTokenProvider.get()}`,
        },
      };

export { authenticationHeader };
