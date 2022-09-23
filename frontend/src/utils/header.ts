import { accessTokenProvider } from './token';

const conditionalAuthenticationHeader = () =>
  accessTokenProvider.get() === ''
    ? {}
    : {
        headers: {
          Authorization: `Bearer ${accessTokenProvider.get()}`,
        },
      };

export { conditionalAuthenticationHeader };
