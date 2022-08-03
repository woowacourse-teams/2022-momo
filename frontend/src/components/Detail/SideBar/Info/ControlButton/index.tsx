import { useNavigate } from 'react-router-dom';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import {
  deleteGroup as requestDeleteGroup,
  joinGroup as requestJoinGroup,
  exitGroup as requestExitGroup,
  requestCloseGroup,
} from 'apis/request/group';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import { loginState, modalState } from 'store/states';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

type ControlButtonProps = Pick<GroupDetailData, 'id' | 'host'> & {
  participants: GroupParticipants;
};

function ControlButton({ id, host, participants }: ControlButtonProps) {
  const { isLogin, user } = useRecoilValue(loginState);
  const setModalState = useSetRecoilState(modalState);
  const navigate = useNavigate();

  const closeGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.GROUP.CONFIRM_CLOSE_REQUEST)) return;

    requestCloseGroup(id)
      .then(() => {
        alert(GUIDE_MESSAGE.GROUP.SUCCESS_CLOSE_REQUEST);
        // TODO: 리렌더링
      })
      .catch(() => {
        alert(ERROR_MESSAGE.GROUP.FAILURE_CLOSE_GROUP);
      });
  };

  const deleteGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.DELETE.CONFIRM_REQUEST)) return;

    requestDeleteGroup(id)
      .then(() => {
        alert(GUIDE_MESSAGE.DELETE.SUCCESS_REQUEST);
        navigate('/');
      })
      .catch(() => {
        alert(ERROR_MESSAGE.DELETE.FAILURE_REQUEST);
      });
  };

  const joinGroup = () => {
    if (!isLogin) {
      alert(GUIDE_MESSAGE.AUTH.NEED_LOGIN);
      setModalState('login');
      return;
    }

    requestJoinGroup(id)
      .then(() => {
        alert(GUIDE_MESSAGE.GROUP.SUCCESS_JOIN_REQUEST);
        // TODO: 리렌더링
      })
      .catch(() => {
        alert(ERROR_MESSAGE.GROUP.FAILURE_JOIN_GROUP);
      });
  };

  const exitGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.GROUP.CONFIRM_EXIT_REQUEST)) return;

    requestExitGroup(id)
      .then(() => {
        alert(GUIDE_MESSAGE.GROUP.SUCCESS_EXIT_REQUEST);
        // TODO: 리렌더링
      })
      .catch(() => {
        alert(ERROR_MESSAGE.GROUP.FAILURE_EXIT_GROUP);
      });
  };

  const isJoined = participants.some(
    participant => participant.id === user?.id,
  );

  if (user?.id === host.id) {
    return (
      <S.HostButtonContainer>
        <S.EarlyClosedButton type="button" onClick={closeGroup}>
          마감하기
        </S.EarlyClosedButton>
        <S.DeleteButton type="button" onClick={deleteGroup}>
          삭제하기
        </S.DeleteButton>
      </S.HostButtonContainer>
    );
  }

  if (isJoined) {
    return (
      <S.ExitButton type="button" onClick={exitGroup}>
        참여 취소
      </S.ExitButton>
    );
  }

  return (
    <S.JoinButton type="button" onClick={joinGroup}>
      참여 신청
    </S.JoinButton>
  );
}

export default ControlButton;
