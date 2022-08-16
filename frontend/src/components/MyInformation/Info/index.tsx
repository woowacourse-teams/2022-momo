import { useState } from 'react';

import { useRecoilValue } from 'recoil';

import { requestChangeName, requestChangePassword } from 'apis/request/user';
import { ReactComponent as CompleteSVG } from 'assets/svg/complete.svg';
import { ReactComponent as PencilSVG } from 'assets/svg/pencil.svg';
import Logo from 'components/svg/Logo';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useInput from 'hooks/useInput';
import useSnackbar from 'hooks/useSnackbar';
import { loginState } from 'store/states';

import * as S from './index.styled';

const svgSize = 20;

function Info() {
  const { user } = useRecoilValue(loginState);

  const { value: name, setValue: setName } = useInput(user?.name || '');
  const { value: password, setValue: setPassword } = useInput('');

  const [isNameEditable, setIsNameEditable] = useState(false);
  const [isPasswordEditable, setIsPasswordEditable] = useState(false);

  const { setMessage } = useSnackbar();

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

  const editValue = (type: 'name' | 'password') => () => {
    switch (type) {
      case 'name':
        requestChangeName(name)
          .then(() => {
            setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_NAME_REQUEST);
            setIsNameEditable(false);
          })
          .catch(() => {
            alert(ERROR_MESSAGE.MEMBER.FAILURE_NAME_REQUEST);
          });

        break;
      case 'password':
        requestChangePassword(password)
          .then(() => {
            setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_PASSWORD_REQUEST);
            setIsPasswordEditable(false);
          })
          .catch(() => {
            alert(ERROR_MESSAGE.MEMBER.FAILURE_PASSWORD_REQUEST);
          });

        break;
    }
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
                onClick={editValue('name')}
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
                onClick={editValue('password')}
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
    </S.Container>
  );
}

export default Info;
