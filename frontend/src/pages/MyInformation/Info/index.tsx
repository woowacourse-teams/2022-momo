import { useState } from 'react';

import { useRecoilState } from 'recoil';

import { requestChangePassword } from 'apis/request/user';
import { BeanSVG } from 'assets/svg';
import ConfirmPasswordModal from 'components/ConfirmPassword';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useInput from 'hooks/useInput';
import useSnackbar from 'hooks/useSnackbar';
import { loginState } from 'store/states';

import Buttons from './Buttons';
import * as S from './index.styled';
import Inputs from './Inputs';

function Info() {
  const [loginInfo, setLoginInfo] = useRecoilState(loginState);

  const { value: name, setValue: setName } = useInput(
    loginInfo.user?.name || '',
  );
  const { value: oldPassword, setValue: setOldPassword } = useInput('');
  const { value: newPassword, setValue: setNewPassword } = useInput('');

  const [isNameEditable, setIsNameEditable] = useState(false);
  const [isPasswordEditable, setIsPasswordEditable] = useState(false);

  const { setMessage } = useSnackbar();

  const editPassword = (oldPassword: string, newPassword: string) => () => {
    requestChangePassword(oldPassword, newPassword)
      .then(() => {
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_PASSWORD_REQUEST);
        setIsPasswordEditable(false);
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
        <Inputs
          loginInfo={loginInfo}
          name={name}
          setName={setName}
          isNameEditable={isNameEditable}
          newPassword={newPassword}
          setNewPassword={setNewPassword}
          isPasswordEditable={isPasswordEditable}
        />
        <Buttons
          loginInfo={loginInfo}
          isNameEditable={isNameEditable}
          isPasswordEditable={isPasswordEditable}
          setIsNameEditable={setIsNameEditable}
          setIsPasswordEditable={setIsPasswordEditable}
          name={name}
          setLoginInfo={setLoginInfo}
        />
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
