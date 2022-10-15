import { useState } from 'react';

import { useRecoilValue } from 'recoil';

import { requestChangePassword } from 'apis/request/user';
import ConfirmPasswordModal from 'components/ConfirmPassword';
import { CLIENT_ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useHandleError from 'hooks/useHandleError';
import useInput from 'hooks/useInput';
import useSnackbar from 'hooks/useSnackbar';
import { loginState } from 'store/states';

import Buttons from './Buttons';
import * as S from './index.styled';
import Inputs from './Inputs';
import LiveBean from './LiveBean';

function Info() {
  const loginInfo = useRecoilValue(loginState);

  const { value: name, setValue: setName } = useInput(
    loginInfo.user?.name || '',
  );
  const {
    value: oldPassword,
    setValue: setOldPassword,
    dangerouslySetValue: dangerouslySetOldPassword,
  } = useInput('');
  const {
    value: newPassword,
    setValue: setNewPassword,
    dangerouslySetValue: dangerouslySetNewPassword,
  } = useInput('');

  const [isNameEditable, setIsNameEditable] = useState(false);
  const [isPasswordEditable, setIsPasswordEditable] = useState(false);

  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const resetPassword = () => {
    dangerouslySetOldPassword('');
    dangerouslySetNewPassword('');
  };

  const editPassword = (oldPassword: string, newPassword: string) => () => {
    requestChangePassword(oldPassword, newPassword)
      .then(() => {
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_PASSWORD_REQUEST);
        setIsPasswordEditable(false);
        resetPassword();
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.MEMBER.FAILURE_PASSWORD_REQUEST);
        }
        handleError(error);
      });
  };

  return (
    <S.Container>
      <LiveBean />
      <S.Right>
        <Inputs
          name={{
            value: name,
            setValue: setName,
            isEditable: isNameEditable,
          }}
          password={{
            value: newPassword,
            setValue: setNewPassword,
            isEditable: isPasswordEditable,
          }}
        />
        <Buttons
          name={{
            value: name,
            isEditable: isNameEditable,
            setIsEditable: setIsNameEditable,
          }}
          password={{
            isEditable: isPasswordEditable,
            setIsEditable: setIsPasswordEditable,
          }}
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
