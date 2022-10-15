import { useRecoilState, useSetRecoilState } from 'recoil';

import { requestChangeName, requestUserInfo } from 'apis/request/user';
import { CompleteSVG, PencilSVG } from 'assets/svg';
import { GUIDE_MESSAGE, CLIENT_ERROR_MESSAGE } from 'constants/message';
import useHandleError from 'hooks/useHandleError';
import useSnackbar from 'hooks/useSnackbar';
import { loginState, modalState } from 'store/states';
import { EditableType } from 'types/user';

import * as S from './index.styled';

const svgSize = 20;

interface InfoButtonsProps {
  name: {
    value: string;
    isEditable: boolean;
    setIsEditable: React.Dispatch<React.SetStateAction<boolean>>;
  };
  password: {
    isEditable: boolean;
    setIsEditable: React.Dispatch<React.SetStateAction<boolean>>;
  };
}

function Buttons({ name, password }: InfoButtonsProps) {
  const [loginInfo, setLoginInfo] = useRecoilState(loginState);

  const setModalFlag = useSetRecoilState(modalState);

  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const changeElementEditable = (type: EditableType) => () => {
    switch (type) {
      case 'name':
        name.setIsEditable(prevState => !prevState);
        break;

      case 'password':
        password.setIsEditable(prevState => !prevState);
        break;

      default:
        // DO NOTHING
        break;
    }
  };

  const showConfirmPasswordModal = () => {
    setModalFlag('confirmPassword');
  };

  const editName = () => {
    requestChangeName(name.value)
      .then(() => {
        setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_NAME_REQUEST);
        name.setIsEditable(false);

        requestUserInfo().then(userInfo => {
          setLoginInfo({ ...loginInfo, user: userInfo });
        });
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.MEMBER.FAILURE_NAME_REQUEST);
        }
        handleError(error);
      });
  };

  return (
    <S.ButtonBox>
      {name.isEditable ? (
        <S.EditButton type="button">
          <CompleteSVG width={svgSize} height={svgSize} onClick={editName} />
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
      {loginInfo.loginType === 'basic' &&
        (password.isEditable ? (
          <S.EditButton type="button">
            <CompleteSVG
              width={svgSize}
              height={svgSize}
              onClick={showConfirmPasswordModal}
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
        ))}
    </S.ButtonBox>
  );
}

export default Buttons;
