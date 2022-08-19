import { useEffect } from 'react';

import { Outlet, useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { BROWSER_PATH } from 'constants/path';
import { loginState } from 'store/states';

function PrivateWrapper() {
  const { isLogin } = useRecoilValue(loginState);

  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      navigate(BROWSER_PATH.BASE);
    }
  }, [isLogin, navigate]);

  return <Outlet />;
}

export default PrivateWrapper;
