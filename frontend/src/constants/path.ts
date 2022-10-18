const BROWSER_PATH = {
  BASE: '/',
  DETAIL: '/detail',
  CREATE: '/create',
  MY_INFORMATION: '/me',
  MY_GROUP: '/me/group',
  OAUTH_GOOGLE: '/auth/google',
  LANDING: '/landing',
};

const API_PATH = {
  GROUP: '/groups',
  JOINED_GROUP: {
    PARTICIPATED: '/groups/me/participated',
    HOSTED: '/groups/me/hosted',
    LIKED: '/groups/me/liked',
  },
  CATEGORY: '/categories',
  PARTICIPANTS: '/participants',
  CLOSE: '/close',
  LIKE: '/like',
  AUTH: {
    LOGIN: '/auth/login',
    GOOGLE_LOGIN: '/auth/oauth2/google/login',
    LOGOUT: '/auth/logout',
    REFRESH_ACCESS_TOKEN: '/auth/token/refresh',
  },
  MEMBER: {
    BASE: '/members',
    NAME: '/members/name',
    PASSWORD: '/members/password',
  },
};

export { BROWSER_PATH, API_PATH };
