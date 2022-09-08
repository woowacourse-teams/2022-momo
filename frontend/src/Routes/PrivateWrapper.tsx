import { useEffect } from 'react';

import { Outlet, useNavigate } from 'react-router-dom';

import useAuth from 'hooks/useAuth';

import { BROWSER_PATH } from 'constants/path';

function PrivateWrapper() {
  const { isLogin } = useAuth();

  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      navigate(BROWSER_PATH.BASE);
    }
  }, [isLogin, navigate]);

  return <Outlet />;
}

export default PrivateWrapper;
