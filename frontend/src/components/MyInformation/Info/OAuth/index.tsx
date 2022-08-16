import { useRecoilState } from 'recoil';

import { getUserInfo, requestChangeName } from 'apis/request/user';
import { ReactComponent as CompleteSVG } from 'assets/svg/complete.svg';
import { ReactComponent as PencilSVG } from 'assets/svg/pencil.svg';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { loginState } from 'store/states';

import * as S from '../index.styled';

interface OAuthProps {
  name: string;
  setName: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isNameEditable: boolean;
  changeNameEditable: () => void;
}

function OAuth({
  name,
  setName,
  isNameEditable,
  changeNameEditable,
}: OAuthProps) {
  const [loginInfo, setLoginInfo] = useRecoilState(loginState);

  const { setMessage } = useSnackbar();

  const editName = () => {
    requestChangeName(name)
      .then(() => {
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_NAME_REQUEST);
        changeNameEditable();

        getUserInfo().then(userInfo => {
          setLoginInfo({ ...loginInfo, user: userInfo });
        });
      })
      .catch(() => {
        alert(ERROR_MESSAGE.MEMBER.FAILURE_NAME_REQUEST);
      });
  };

  return (
    <>
      <S.InputBox>
        <S.Label>
          아이디
          <S.Input value={loginInfo.user?.userId} disabled />
        </S.Label>
        <S.Label>
          닉네임
          <S.Input value={name} onChange={setName} disabled={!isNameEditable} />
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
