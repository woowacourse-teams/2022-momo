import { useRecoilValue } from 'recoil';

import { ReactComponent as CompleteSVG } from 'assets/svg/complete.svg';
import { ReactComponent as PencilSVG } from 'assets/svg/pencil.svg';
import { loginState } from 'store/states';

import * as S from '../index.styled';

interface OAuthProps {
  name: string;
  setName: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isNameEditable: boolean;
  editName: () => void;
  changeNameEditable: () => void;
}

function OAuth({
  name,
  setName,
  isNameEditable,
  editName,
  changeNameEditable,
}: OAuthProps) {
  const { user } = useRecoilValue(loginState);

  return (
    <>
      <S.InputBox>
        <S.Label>
          아이디
          <S.Input type="text" value={user?.userId || ''} disabled />
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
      </S.InputBox>
      <S.ButtonBox>
        {isNameEditable ? (
          <S.EditButton type="button">
            <CompleteSVG width={20} height={20} onClick={editName} />
          </S.EditButton>
        ) : (
          <S.EditButton type="button">
            <PencilSVG width={20} height={20} onClick={changeNameEditable} />
          </S.EditButton>
        )}
      </S.ButtonBox>
    </>
  );
}

export default OAuth;
