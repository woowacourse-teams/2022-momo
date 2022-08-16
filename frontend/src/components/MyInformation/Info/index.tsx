import { useState } from 'react';

import { useRecoilValue, useSetRecoilState } from 'recoil';

import ConfirmPasswordModal from 'components/ConfirmPassword';
import Logo from 'components/svg/Logo';
import useInput from 'hooks/useInput';
import { loginState, modalState } from 'store/states';

import Basic from './Basic';
import * as S from './index.styled';
import OAuth from './OAuth';

function Info() {
  const setModalFlag = useSetRecoilState(modalState);
  const { user, loginType } = useRecoilValue(loginState);

  const { value: name, setValue: setName } = useInput(user?.name || '');
  const { value: password, setValue: setPassword } = useInput('');

  const [type, setType] = useState('');
  const [isNameEditable, setIsNameEditable] = useState(false);
  const [isPasswordEditable, setIsPasswordEditable] = useState(false);

  const changeElementEditable = (type: 'name' | 'password') => () => {
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

  const showConfirmPasswordModal = (newType: 'name' | 'password') => () => {
    setType(newType);
    setModalFlag('confirmPassword');
  };

  return (
    <S.Container>
      <div>
        <Logo color="#000000" width={200} />
      </div>
      <S.Right>
        {loginType === 'basic' ? (
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
            changeNameEditable={changeElementEditable('name')}
          />
        )}
      </S.Right>
      {loginType === 'basic' && (
        <ConfirmPasswordModal
          type={type}
          newValue={type === 'name' ? name : password}
          setIsEditable={
            type === 'name' ? setIsNameEditable : setIsPasswordEditable
          }
        />
      )}
    </S.Container>
  );
}

export default Info;
