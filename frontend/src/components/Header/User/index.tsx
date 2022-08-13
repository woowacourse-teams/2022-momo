import { useEffect, useState } from 'react';

import { useLocation, useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { getUserInfo } from 'apis/request/user';
import { GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import useClosingState from 'hooks/useClosingState';
import { accessTokenState, loginState } from 'store/states';

import * as S from './index.styled';

const dropdownAnimationTime = 300;

function User() {
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const [{ isLogin, user }, setLoginInfo] = useRecoilState(loginState);
  const [isShownDropdown, setIsShownDropdown] = useState(false);
  const { isClosing, close } = useClosingState(dropdownAnimationTime, () => {
    setIsShownDropdown(false);
  });
  const navigate = useNavigate();
  const { pathname } = useLocation();

  useEffect(() => {
    if (!accessToken) return;

    getUserInfo().then(userInfo => {
      setLoginInfo({ isLogin: true, user: userInfo });
    });
  }, [accessToken, setLoginInfo]);

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

    setLoginInfo({ isLogin: false });
    setAccessToken('');
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
          <S.User onClick={navigateToLocation(BROWSER_PATH.INFO)}>
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
