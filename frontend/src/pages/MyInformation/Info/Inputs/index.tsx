import { LoginState } from 'types/user';

import * as S from './index.styled';

interface InfoInputsProps {
  loginInfo: LoginState;
  name: string;
  setName: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isNameEditable: boolean;
  newPassword: string;
  setNewPassword: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isPasswordEditable: boolean;
}

function Inputs({
  loginInfo,
  name,
  setName,
  isNameEditable,
  newPassword,
  setNewPassword,
  isPasswordEditable,
}: InfoInputsProps) {
  return (
    <S.InputBox>
      <S.Label>
        아이디
        <S.Input type="text" value={loginInfo.user?.userId || ''} disabled />
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
  );
}

export default Inputs;
