import { useRecoilValue } from 'recoil';

import { ReactComponent as CompleteSVG } from 'assets/svg/complete.svg';
import { ReactComponent as PencilSVG } from 'assets/svg/pencil.svg';
import { loginState } from 'store/states';

import * as S from '../index.styled';

interface BasicProps {
  nameSet: {
    name: string;
    setName: (e: React.ChangeEvent<HTMLInputElement>) => void;
    isNameEditable: boolean;
  };
  passwordSet: {
    password: string;
    setPassword: (e: React.ChangeEvent<HTMLInputElement>) => void;
    isPasswordEditable: boolean;
  };
  changeElementEditable: (type: 'name' | 'password') => () => void;
  showConfirmPasswordModal: (newType: 'name' | 'password') => () => void;
}

function Basic({
  nameSet: { name, setName, isNameEditable },
  passwordSet: { password, setPassword, isPasswordEditable },
  changeElementEditable,
  showConfirmPasswordModal,
}: BasicProps) {
  const { user } = useRecoilValue(loginState);

  return (
    <>
      <S.InputBox>
        <S.Label>
          아이디
          <S.Input type="text" value={user?.userId} disabled />
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
        <S.Label>
          비밀번호
          <S.Input
            type="password"
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
              width={20}
              height={20}
              onClick={showConfirmPasswordModal('name')}
            />
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
        {isPasswordEditable ? (
          <S.EditButton type="button">
            <CompleteSVG
              width={20}
              height={20}
              onClick={showConfirmPasswordModal('password')}
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
    </>
  );
}

export default Basic;
