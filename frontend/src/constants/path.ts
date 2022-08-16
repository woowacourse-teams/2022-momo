const BROWSER_PATH = {
  BASE: '/',
  DETAIL: '/detail',
  CREATE: '/create',
  MY_INFORMATION: '/myinformation',
  MY_GROUP: '/mygroup',
  OAUTH_GOOGLE: '/auth/google',
};

const API_PATH = {
  GROUP: '/groups',
  JOINED_GROUP: '/groups/me',
  CATEGORY: '/categories',
  PARTICIPANTS: '/participants',
  CLOSE: '/close',
  SIGNUP: '/auth/signup',
  LOGIN: '/auth/login',
  GOOGLE_LOGIN: '/auth/oauth2/google/login',
  LOGOUT: '/auth/logout',
  MEMBERS: '/members',
  NAME: '/members/name',
  PASSWORD: '/members/password',
};

export { BROWSER_PATH, API_PATH };
