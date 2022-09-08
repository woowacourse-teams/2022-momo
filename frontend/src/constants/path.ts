const BROWSER_PATH = {
  BASE: '/',
  DETAIL: '/detail',
  CREATE: '/create',
  MY_INFORMATION: '/me',
  MY_GROUP: '/me/group',
  OAUTH_GOOGLE: '/auth/google',
};

const API_PATH = {
  GROUP: '/groups',
  PARTICIPATED_GROUP: '/groups/me/participated',
  HOSTED_GROUP: '/groups/me/hosted',
  LIKED_GROUP: '/groups/me/liked',
  CATEGORY: '/categories',
  PARTICIPANTS: '/participants',
  CLOSE: '/close',
  SIGNUP: '/auth/signup',
  LOGIN: '/auth/login',
  GOOGLE_LOGIN: '/auth/oauth2/google/login',
  LOGOUT: '/auth/logout',
  REFRESH_ACCESS_TOKEN: '/auth/token/refresh',
  MEMBERS: '/members',
  NAME: '/members/name',
  PASSWORD: '/members/password',
};

export { BROWSER_PATH, API_PATH };
