import { useRecoilValue } from 'recoil';

import { loginState } from 'store/states';

import * as S from './index.styled';

interface InfoInputsProps {
  name: {
    value: string;
    setValue: (e: React.ChangeEvent<HTMLInputElement>) => void;
    isEditable: boolean;
  };
  password: {
    value: string;
    setValue: (e: React.ChangeEvent<HTMLInputElement>) => void;
    isEditable: boolean;
  };
}

function Inputs({ name, password }: InfoInputsProps) {
  const loginInfo = useRecoilValue(loginState);

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
          value={name.value}
          onChange={name.setValue}
          disabled={!name.isEditable}
        />
      </S.Label>
      {loginInfo.loginType === 'basic' && (
        <S.Label>
          비밀번호
          <S.Input
            type="password"
            placeholder="********"
            value={password.value}
            onChange={password.setValue}
            disabled={!password.isEditable}
          />
        </S.Label>
      )}
    </S.InputBox>
  );
}

export default Inputs;
