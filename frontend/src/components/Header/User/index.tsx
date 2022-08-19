import { useEffect, useState } from 'react';

import { useLocation, useNavigate } from 'react-router-dom';
import { useRecoilValue, useResetRecoilState, useSetRecoilState } from 'recoil';

import { requestLogout } from 'apis/request/auth';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import useClosingState from 'hooks/useClosingState';
import useSnackbar from 'hooks/useSnackbar';
import { accessTokenState, loginState, refreshTokenState } from 'store/states';

import * as S from './index.styled';

const dropdownAnimationTime = 300;

function User() {
  const setAccessToken = useSetRecoilState(accessTokenState);
  const setRefreshToken = useSetRecoilState(refreshTokenState);
  const { isLogin, user } = useRecoilValue(loginState);
  const resetLoginInfo = useResetRecoilState(loginState);

  const [isShownDropdown, setIsShownDropdown] = useState(false);
  const { isClosing, close } = useClosingState(dropdownAnimationTime, () => {
    setIsShownDropdown(false);
  });

  const { setMessage } = useSnackbar();

  const navigate = useNavigate();
  const { pathname } = useLocation();

  useEffect(() => {
    setIsShownDropdown(false);
  }, [pathname, isLogin]);

  const navigateToLocation = (location: string) => () => {
    navigate(location);
  };

  const toggleDropdownState = () => {
    if (isShownDropdown) {
      close();
      return;
    }

    setIsShownDropdown(true);
  };

  const logout = () => {
    if (!window.confirm(GUIDE_MESSAGE.AUTH.CONFIRM_LOGOUT)) return;

    requestLogout()
      .then(() => {
        resetLoginInfo();

        setAccessToken('');
        setRefreshToken('');

        setMessage(GUIDE_MESSAGE.AUTH.LOGOUT_SUCCESS);

        navigate(BROWSER_PATH.BASE);
      })
      .catch(() => {
        alert(ERROR_MESSAGE.AUTH.FAILURE_LOGOUT_REQUEST);
      });
  };

  return (
    <S.Container>
      <S.ToggleButton type="button" onClick={toggleDropdownState}>
        <S.Profile width="2rem">❤️</S.Profile>
      </S.ToggleButton>
      {isShownDropdown && (
        <S.Dropdown
          className={isClosing ? 'close' : ''}
          animationTime={dropdownAnimationTime}
        >
          <S.User onClick={navigateToLocation(BROWSER_PATH.MY_INFORMATION)}>
            <S.Profile width="4rem">❤️</S.Profile>
            <div>{user?.name}</div>
          </S.User>
          <S.Option onClick={navigateToLocation(BROWSER_PATH.MY_GROUP)}>
            내 모임
          </S.Option>
          <S.Option onClick={logout}>로그아웃</S.Option>
        </S.Dropdown>
      )}
    </S.Container>
  );
}

export default User;
