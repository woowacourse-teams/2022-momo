import {
  Main,
  Detail,
  Create,
  MyInformation,
  NotFound,
  Auth,
  MyGroup,
} from 'pages';
import { Route, Routes as BrowserRoutes } from 'react-router-dom';

import { BROWSER_PATH } from 'constants/path';

function Routes() {
  return (
    <BrowserRoutes>
      <Route path={BROWSER_PATH.BASE} element={<Main />} />
      <Route path={BROWSER_PATH.DETAIL}>
        <Route path=":id" element={<Detail />} />
      </Route>
      <Route path={BROWSER_PATH.CREATE} element={<Create />} />
      <Route path={BROWSER_PATH.MY_INFORMATION} element={<MyInformation />} />
      <Route path={BROWSER_PATH.OAUTH_GOOGLE} element={<Auth />} />
      <Route path={BROWSER_PATH.MY_GROUP} element={<MyGroup />} />
      <Route path="*" element={<NotFound />} />
    </BrowserRoutes>
  );
}

export default Routes;
