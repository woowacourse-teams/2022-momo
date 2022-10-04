import { SetterOrUpdater, useSetRecoilState } from 'recoil';

import { requestChangeName, requestUserInfo } from 'apis/request/user';
import { CompleteSVG, PencilSVG } from 'assets/svg';
import { GUIDE_MESSAGE, ERROR_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { modalState } from 'store/states';
import { EditableType, LoginState } from 'types/user';

import * as S from './index.styled';

interface InfoButtonsProps {
  loginInfo: LoginState;
  isNameEditable: boolean;
  isPasswordEditable: boolean;
  setIsNameEditable: React.Dispatch<React.SetStateAction<boolean>>;
  setIsPasswordEditable: React.Dispatch<React.SetStateAction<boolean>>;
  name: string;
  setLoginInfo: SetterOrUpdater<LoginState>;
}

function Buttons({
  loginInfo,
  isNameEditable,
  isPasswordEditable,
  setIsNameEditable,
  setIsPasswordEditable,
  name,
  setLoginInfo,
}: InfoButtonsProps) {
  const setModalFlag = useSetRecoilState(modalState);
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

  const showConfirmPasswordModal = () => () => {
    setModalFlag('confirmPassword');
  };

  const editName = () => {
    requestChangeName(name)
      .then(() => {
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_NAME_REQUEST);
        setIsNameEditable(false);

        requestUserInfo().then(userInfo => {
          setLoginInfo({ ...loginInfo, user: userInfo });
        });
      })
      .catch(() => {
        alert(ERROR_MESSAGE.MEMBER.FAILURE_NAME_REQUEST);
      });
  };

  return (
    <S.ButtonBox>
      {isNameEditable ? (
        <S.EditButton type="button">
          <CompleteSVG width={20} height={20} onClick={editName} />
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
      {loginInfo.loginType === 'basic' &&
        (isPasswordEditable ? (
          <S.EditButton type="button">
            <CompleteSVG
              width={20}
              height={20}
              onClick={showConfirmPasswordModal()}
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
        ))}
    </S.ButtonBox>
  );
}

export default Buttons;
