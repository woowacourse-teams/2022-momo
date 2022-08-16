import { useState } from 'react';

import { useRecoilState, useSetRecoilState } from 'recoil';

import {
  getUserInfo,
  requestChangeName,
  requestChangePassword,
} from 'apis/request/user';
import ConfirmPasswordModal from 'components/ConfirmPassword';
import Logo from 'components/svg/Logo';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useInput from 'hooks/useInput';
import useSnackbar from 'hooks/useSnackbar';
import { loginState, modalState } from 'store/states';
import { EditableType } from 'types/user';

import Basic from './Basic';
import * as S from './index.styled';
import OAuth from './OAuth';

function Info() {
  const setModalFlag = useSetRecoilState(modalState);
  const [loginInfo, setLoginInfo] = useRecoilState(loginState);

  const { value: name, setValue: setName } = useInput(
    loginInfo.user?.name || '',
  );
  const { value: password, setValue: setPassword } = useInput('');

  const [type, setType] = useState('');
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

  const showConfirmPasswordModal = (newType: EditableType) => () => {
    setType(newType);
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

  const editPassword = () => {
    requestChangePassword(password)
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
      <div>
        <Logo color="#000000" width={200} />
      </div>
      <S.Right>
        {loginInfo.loginType === 'basic' ? (
          <Basic
            nameSet={{ name, setName, isNameEditable }}
            passwordSet={{ password, setPassword, isPasswordEditable }}
            changeElementEditable={changeElementEditable}
            showConfirmPasswordModal={showConfirmPasswordModal}
          />
        ) : (
          <OAuth
            name={name}
            setName={setName}
            isNameEditable={isNameEditable}
            editName={editName}
            changeNameEditable={changeElementEditable('name')}
          />
        )}
      </S.Right>
      {loginInfo.loginType === 'basic' && (
        <ConfirmPasswordModal
          editValue={type === 'name' ? editName : editPassword}
        />
      )}
    </S.Container>
  );
}

export default Info;
