import { useEffect, useState } from 'react';

import { useLocation, useNavigate } from 'react-router-dom';

import { requestLogout } from 'apis/request/auth';
import { SignInSVG } from 'assets/svg';
import { CLIENT_ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import useAuth from 'hooks/useAuth';
import useClosingState from 'hooks/useClosingState';
import useHandleError from 'hooks/useHandleError';
import useSnackbar from 'hooks/useSnackbar';
import theme from 'styles/theme';

import * as S from './index.styled';

const dropdownAnimationTime = 300;

function User() {
  const { isLogin, user, resetAuth } = useAuth();

  const [isShownDropdown, setIsShownDropdown] = useState(false);
  const { isClosing, close } = useClosingState(dropdownAnimationTime, () => {
    setIsShownDropdown(false);
  });

  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

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
        resetAuth();
        setMessage(GUIDE_MESSAGE.AUTH.LOGOUT_SUCCESS);

        navigate(BROWSER_PATH.BASE);
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.AUTH.FAILURE_LOGOUT_REQUEST);
        }

        handleError(error);
      });
  };

  return (
    <>
      {document.body.clientWidth > theme.breakpoints.md ? (
        <S.Container>
          <S.ToggleButton type="button" onClick={toggleDropdownState}>
            <S.Profile width="32px">❤️</S.Profile>
          </S.ToggleButton>
          {isShownDropdown && (
            <S.Dropdown
              className={isClosing ? 'close' : ''}
              animationTime={dropdownAnimationTime}
            >
              <S.User onClick={navigateToLocation(BROWSER_PATH.MY_INFORMATION)}>
                <S.Profile width="64px">❤️</S.Profile>
                <div>{user?.name}</div>
              </S.User>
              <S.Option onClick={navigateToLocation(BROWSER_PATH.MY_GROUP)}>
                내 모임
              </S.Option>
              <S.Option onClick={logout}>로그아웃</S.Option>
            </S.Dropdown>
          )}
        </S.Container>
      ) : (
        <S.SvgWrapper>
          <SignInSVG onClick={logout} />
        </S.SvgWrapper>
      )}
    </>
  );
}

export default User;
