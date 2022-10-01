import { useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import {
  requestDeleteGroup,
  requestJoinGroup,
  requestExitGroup,
  requestCloseGroup,
} from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import { CLIENT_ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import useHandleError from 'hooks/useHandleError';
import useModal from 'hooks/useModal';
import useSnackbar from 'hooks/useSnackbar';
import { loginState } from 'store/states';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

interface ControlButtonProps
  extends Pick<GroupDetailData, 'id' | 'host' | 'capacity' | 'finished'> {
  participants: GroupParticipants;
}

function ControlButton({
  id,
  host,
  capacity,
  finished,
  participants,
}: ControlButtonProps) {
  const { isLogin, user } = useRecoilValue(loginState);

  const { showLoginModal } = useModal();
  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

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
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_CLOSE_GROUP);
        }
        handleError(error);
      });
  };

  const deleteGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.DELETE.CONFIRM_REQUEST)) return;

    requestDeleteGroup(id)
      .then(() => {
        setMessage(GUIDE_MESSAGE.DELETE.SUCCESS_REQUEST);

        navigate(BROWSER_PATH.BASE);
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.DELETE.FAILURE_REQUEST);
        }
        handleError(error);
      });
  };

  const joinGroup = () => {
    if (!isLogin) {
      alert(GUIDE_MESSAGE.AUTH.NEED_LOGIN);
      showLoginModal();

      return;
    }

    requestJoinGroup(id)
      .then(() => {
        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_JOIN_REQUEST);
        refetch();
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_JOIN_GROUP);
        }
        handleError(error);
      });
  };

  const exitGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.GROUP.CONFIRM_EXIT_REQUEST)) return;

    requestExitGroup(id)
      .then(() => {
        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_EXIT_REQUEST);
        refetch();
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_EXIT_GROUP);
        }
        handleError(error);
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

  if (participants.length === capacity) {
    return <S.DisableButton>참여 인원이 가득 찼어요</S.DisableButton>;
  }

  return (
    <S.JoinButton type="button" onClick={joinGroup}>
      참여 신청
    </S.JoinButton>
  );
}

export default ControlButton;
