import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { requestWithdrawal } from 'apis/request/user';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { accessTokenState, loginState } from 'store/states';

import * as S from './index.styled';

function Withdrawal() {
  const setLoginInfo = useSetRecoilState(loginState);
  const setAccessToken = useSetRecoilState(accessTokenState);

  const { setMessage } = useSnackbar();

  const navigate = useNavigate();

  const withdrawal = () => {
    if (!window.confirm(GUIDE_MESSAGE.MEMBER.CONFIRM_WITHDRAWAL_REQUEST))
      return;

    requestWithdrawal()
      .then(() => {
        setLoginInfo({ isLogin: false });
        setAccessToken('');
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_WITHDRAWAL_REQUEST);

        navigate('/');
      })
      .catch(() => {
        alert(ERROR_MESSAGE.MEMBER.FAILURE_WITHDRAWAL_REQUEST);
      });
  };

  return (
    <S.Wrapper>
      <S.Button type="button" onClick={withdrawal}>
        회원 탈퇴
      </S.Button>
    </S.Wrapper>
  );
}

export default Withdrawal;
