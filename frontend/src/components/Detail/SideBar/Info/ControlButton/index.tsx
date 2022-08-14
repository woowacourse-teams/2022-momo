import { useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import {
  deleteGroup as requestDeleteGroup,
  joinGroup as requestJoinGroup,
  exitGroup as requestExitGroup,
  requestCloseGroup,
} from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { loginState, modalState } from 'store/states';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

type ControlButtonProps = Pick<GroupDetailData, 'id' | 'host' | 'finished'> & {
  participants: GroupParticipants;
};

function ControlButton({
  id,
  host,
  finished,
  participants,
}: ControlButtonProps) {
  const { isLogin, user } = useRecoilValue(loginState);
  const setModalState = useSetRecoilState(modalState);

  const { setMessage } = useSnackbar();

  const navigate = useNavigate();

  const queryClient = useQueryClient();
  const refetch = () => {
    queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);
    queryClient.invalidateQueries(`${QUERY_KEY.GROUP_PARTICIPANTS}/${id}`);
  };

  const closeGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.GROUP.CONFIRM_CLOSE_REQUEST)) return;

    requestCloseGroup(id)
      .then(() => {
        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_CLOSE_REQUEST);
        refetch();
      })
      .catch(() => {
        alert(ERROR_MESSAGE.GROUP.FAILURE_CLOSE_GROUP);
      });
  };

  const deleteGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.DELETE.CONFIRM_REQUEST)) return;

    requestDeleteGroup(id)
      .then(() => {
        setMessage(GUIDE_MESSAGE.DELETE.SUCCESS_REQUEST);
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
        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_JOIN_REQUEST);
        refetch();
      })
      .catch(() => {
        alert(ERROR_MESSAGE.GROUP.FAILURE_JOIN_GROUP);
      });
  };

  const exitGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.GROUP.CONFIRM_EXIT_REQUEST)) return;

    requestExitGroup(id)
      .then(() => {
        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_EXIT_REQUEST);
        refetch();
      })
      .catch(() => {
        alert(ERROR_MESSAGE.GROUP.FAILURE_EXIT_GROUP);
      });
  };

  const isJoined = participants.some(
    participant => participant.id === user?.id,
  );

  if (finished) {
    return <S.DisableButton>마감되었어요</S.DisableButton>;
  }

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
