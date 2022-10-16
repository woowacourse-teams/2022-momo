import { useNavigate, useSearchParams } from 'react-router-dom';

import { requestGoogleLogin } from 'apis/request/auth';
import { requestUserInfo } from 'apis/request/user';
import { Loading } from 'components/Animation';
import { GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import useAuth from 'hooks/useAuth';
import useHandleError from 'hooks/useHandleError';
import useSnackbar from 'hooks/useSnackbar';
import { prevLocationProvider } from 'utils/location';

import * as S from './index.styled';

function Auth() {
  const { setAuth, setLogin } = useAuth();

  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const navigate = useNavigate();

  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  if (code) {
    requestGoogleLogin(code)
      .then(token => {
        setAuth(token);

        requestUserInfo().then(userInfo => {
          setLogin('oauth', userInfo);
          setMessage(GUIDE_MESSAGE.AUTH.LOGIN_SUCCESS);
        });

        const prevLocation = prevLocationProvider.get() || BROWSER_PATH.BASE;
        prevLocationProvider.remove();

        navigate(prevLocation, { replace: true });
      })
      .catch(error => {
        handleError(error);

        navigate(BROWSER_PATH.BASE);
      });
  }

  return (
    <S.PageContainer>
      <Loading />
    </S.PageContainer>
  );
}

export default Auth;
