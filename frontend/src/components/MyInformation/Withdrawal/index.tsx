import { useNavigate } from 'react-router-dom';
import { useResetRecoilState, useSetRecoilState } from 'recoil';

import { requestWithdrawal } from 'apis/request/user';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import useSnackbar from 'hooks/useSnackbar';
import { accessTokenState, loginState } from 'store/states';

import * as S from './index.styled';

function Withdrawal() {
  const resetLoginInfo = useResetRecoilState(loginState);
  const setAccessToken = useSetRecoilState(accessTokenState);

  const { setMessage } = useSnackbar();

  const navigate = useNavigate();

  const withdrawal = () => {
    if (!window.confirm(GUIDE_MESSAGE.MEMBER.CONFIRM_WITHDRAWAL_REQUEST))
      return;

    requestWithdrawal()
      .then(() => {
        resetLoginInfo();
        setAccessToken('');
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_WITHDRAWAL_REQUEST);

        navigate(BROWSER_PATH.BASE);
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
