import { useEffect } from 'react';

import { Outlet, useNavigate } from 'react-router-dom';

import { BROWSER_PATH } from 'constants/path';
import useAuth from 'hooks/useAuth';

function PrivateWrapper() {
  // TODO: 새로고침 시 isLogin이 false라서 privatePage들은 홈으로 연결되는 버그
  const { isLogin } = useAuth();

  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      navigate(BROWSER_PATH.BASE, { replace: true });
    }
  }, [isLogin, navigate]);

  return <Outlet />;
}

export default PrivateWrapper;
