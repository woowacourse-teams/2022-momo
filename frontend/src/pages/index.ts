import { lazy } from 'react';

const Main = lazy(() => import('./Main'));
const Detail = lazy(() => import('./Detail'));
const Create = lazy(() => import('./Create'));
const MyInformation = lazy(() => import('./MyInformation'));
const Auth = lazy(() => import('./Auth'));
const MyGroup = lazy(() => import('./MyGroup'));
const Landing = lazy(() => import('./Landing'));
const NotFound = lazy(() => import('./NotFound'));

export {
  Main,
  Detail,
  Create,
  MyInformation,
  Auth,
  MyGroup,
  Landing,
  NotFound,
};
