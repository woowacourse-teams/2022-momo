import { useEffect } from 'react';

import { Outlet, useNavigate } from 'react-router-dom';

import { BROWSER_PATH } from 'constants/path';
import useAuth from 'hooks/useAuth';

function PrivateWrapper() {
  const { isLogin, accessToken } = useAuth();

  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin && !accessToken) {
      navigate(BROWSER_PATH.BASE, { replace: true });
    }
  }, [isLogin, accessToken, navigate]);

  return <Outlet />;
}

export default PrivateWrapper;
