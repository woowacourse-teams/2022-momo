import { useSearchParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { requestGoogleLogin } from 'apis/request/auth';
import { getUserInfo } from 'apis/request/user';
import { Loading } from 'components/@shared/Animation';
import { GUIDE_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { accessTokenState, loginState } from 'store/states';
import { showErrorMessage } from 'utils/errorController';

import * as S from './index.styled';

function Auth() {
  const setAccessToken = useSetRecoilState(accessTokenState);
  const setLoginInfo = useSetRecoilState(loginState);

  const { setMessage } = useSnackbar();

  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  if (code) {
    requestGoogleLogin(code)
      .then(accessToken => {
        setAccessToken(accessToken);

        getUserInfo().then(userInfo => {
          setLoginInfo({ isLogin: true, user: userInfo });
          setMessage(GUIDE_MESSAGE.AUTH.LOGIN_SUCCESS);
          navigate('/');
        });
      })
      .catch(({ message }) => {
        alert(showErrorMessage(message));
        navigate('/');
      });
  }

  return (
    <S.PageContainer>
      <Loading />
    </S.PageContainer>
  );
}

export default Auth;
