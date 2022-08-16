import { useState } from 'react';

import { useRecoilValue, useSetRecoilState } from 'recoil';

import { ReactComponent as CompleteSVG } from 'assets/svg/complete.svg';
import { ReactComponent as PencilSVG } from 'assets/svg/pencil.svg';
import ConfirmPasswordModal from 'components/ConfirmPassword';
import Logo from 'components/svg/Logo';
import useInput from 'hooks/useInput';
import { loginState, modalState } from 'store/states';

import * as S from './index.styled';

const svgSize = 20;

function Info() {
  const setModalFlag = useSetRecoilState(modalState);
  const { user } = useRecoilValue(loginState);

  const { value: name, setValue: setName } = useInput(user?.name || '');
  const { value: password, setValue: setPassword } = useInput('');

  const [type, setType] = useState('');
  const [isNameEditable, setIsNameEditable] = useState(false);
  const [isPasswordEditable, setIsPasswordEditable] = useState(false);

  const changeElementEditable = (type: 'name' | 'password') => () => {
    switch (type) {
      case 'name':
        setIsNameEditable(true);

        break;
      case 'password':
        setIsPasswordEditable(true);

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
        <S.InputBox>
          <S.Label>
            아이디
            <S.Input value={user?.id} disabled />
          </S.Label>
          <S.Label>
            닉네임
            <S.Input
              value={name}
              onChange={setName}
              disabled={!isNameEditable}
            />
          </S.Label>
          <S.Label>
            비밀번호
            <S.Input
              value={password}
              onChange={setPassword}
              disabled={!isPasswordEditable}
            />
          </S.Label>
        </S.InputBox>
        <S.ButtonBox>
          {isNameEditable ? (
            <S.EditButton type="button">
              <CompleteSVG
                width={svgSize}
                height={svgSize}
                onClick={showConfirmPasswordModal('name')}
              />
            </S.EditButton>
          ) : (
            <S.EditButton type="button">
              <PencilSVG
                width={svgSize}
                height={svgSize}
                onClick={changeElementEditable('name')}
              />
            </S.EditButton>
          )}
          {isPasswordEditable ? (
            <S.EditButton type="button">
              <CompleteSVG
                width={svgSize}
                height={svgSize}
                onClick={showConfirmPasswordModal('password')}
              />
            </S.EditButton>
          ) : (
            <S.EditButton type="button">
              <PencilSVG
                width={svgSize}
                height={svgSize}
                onClick={changeElementEditable('password')}
              />
            </S.EditButton>
          )}
        </S.ButtonBox>
      </S.Right>
      <ConfirmPasswordModal
        type={type}
        newValue={type === 'name' ? name : password}
        setIsEditable={
          type === 'name' ? setIsNameEditable : setIsPasswordEditable
        }
      />
    </S.Container>
  );
}

export default Info;
