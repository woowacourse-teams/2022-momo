import { useState } from 'react';

import { useRecoilState, useSetRecoilState } from 'recoil';

import {
  getUserInfo,
  requestChangeName,
  requestChangePassword,
} from 'apis/request/user';
import { ReactComponent as BeanSVG } from 'assets/svg/bean.svg';
import { ReactComponent as CompleteSVG } from 'assets/svg/complete.svg';
import { ReactComponent as PencilSVG } from 'assets/svg/pencil.svg';
import ConfirmPasswordModal from 'components/ConfirmPassword';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useInput from 'hooks/useInput';
import useSnackbar from 'hooks/useSnackbar';
import { loginState, modalState } from 'store/states';
import { EditableType } from 'types/user';

import * as S from './index.styled';

function Info() {
  const setModalFlag = useSetRecoilState(modalState);
  const [loginInfo, setLoginInfo] = useRecoilState(loginState);

  const { value: name, setValue: setName } = useInput(
    loginInfo.user?.name || '',
  );
  const { value: oldPassword, setValue: setOldPassword } = useInput('');
  const { value: newPassword, setValue: setNewPassword } = useInput('');

  const [isNameEditable, setIsNameEditable] = useState(false);
  const [isPasswordEditable, setIsPasswordEditable] = useState(false);

  const { setMessage } = useSnackbar();

  const changeElementEditable = (type: EditableType) => () => {
    switch (type) {
      case 'name':
        setIsNameEditable(prevState => !prevState);
        break;

      case 'password':
        setIsPasswordEditable(prevState => !prevState);
        break;

      default:
        // DO NOTHING
        break;
    }
  };

  const showConfirmPasswordModal = () => () => {
    setModalFlag('confirmPassword');
  };

  const editName = () => {
    requestChangeName(name)
      .then(() => {
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_NAME_REQUEST);
        setIsNameEditable(false);

        getUserInfo().then(userInfo => {
          setLoginInfo({ ...loginInfo, user: userInfo });
        });
      })
      .catch(() => {
        alert(ERROR_MESSAGE.MEMBER.FAILURE_NAME_REQUEST);
      });
  };

  const editPassword = (oldPassword: string, newPassword: string) => () => {
    requestChangePassword(oldPassword, newPassword)
      .then(() => {
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_PASSWORD_REQUEST);
        setIsPasswordEditable(false);

        getUserInfo().then(userInfo => {
          setLoginInfo({ ...loginInfo, user: userInfo });
        });
      })
      .catch(() => {
        alert(ERROR_MESSAGE.MEMBER.FAILURE_PASSWORD_REQUEST);
      });
  };

  return (
    <S.Container>
      <S.LiveBeanBox>
        <S.LiveBean className="reverse">
          <BeanSVG width={100} height={100} />
        </S.LiveBean>
        <S.LiveBean>
          <BeanSVG width={100} height={100} />
        </S.LiveBean>
      </S.LiveBeanBox>
      <S.Right>
        <S.InputBox>
          <S.Label>
            아이디
            <S.Input
              type="text"
              value={loginInfo.user?.userId || ''}
              disabled
            />
          </S.Label>
          <S.Label>
            닉네임
            <S.Input
              type="text"
              value={name}
              onChange={setName}
              disabled={!isNameEditable}
            />
          </S.Label>
          {loginInfo.loginType === 'basic' && (
            <S.Label>
              비밀번호
              <S.Input
                type="password"
                placeholder="********"
                value={newPassword}
                onChange={setNewPassword}
                disabled={!isPasswordEditable}
              />
            </S.Label>
          )}
        </S.InputBox>
        <S.ButtonBox>
          {isNameEditable ? (
            <S.EditButton type="button">
              <CompleteSVG width={20} height={20} onClick={editName} />
            </S.EditButton>
          ) : (
            <S.EditButton type="button">
              <PencilSVG
                width={20}
                height={20}
                onClick={changeElementEditable('name')}
              />
            </S.EditButton>
          )}
          {loginInfo.loginType === 'basic' && isPasswordEditable ? (
            <S.EditButton type="button">
              <CompleteSVG
                width={20}
                height={20}
                onClick={showConfirmPasswordModal()}
              />
            </S.EditButton>
          ) : (
            <S.EditButton type="button">
              <PencilSVG
                width={20}
                height={20}
                onClick={changeElementEditable('password')}
              />
            </S.EditButton>
          )}
        </S.ButtonBox>
      </S.Right>
      {loginInfo.loginType === 'basic' && (
        <ConfirmPasswordModal
          confirmPassword={oldPassword}
          setConfirmPassword={setOldPassword}
          editPassword={editPassword(oldPassword, newPassword)}
        />
      )}
    </S.Container>
  );
}

export default Info;
