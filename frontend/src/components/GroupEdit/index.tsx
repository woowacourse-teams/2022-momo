import { useQueryClient } from 'react-query';
import { useLocation } from 'react-router-dom';
import { useRecoilValue, useResetRecoilState } from 'recoil';

import { requestEditGroup } from 'apis/request/group';
import Modal from 'components/Modal';
import { QUERY_KEY } from 'constants/key';
import { GUIDE_MESSAGE } from 'constants/message';
import useCreateState from 'hooks/useCreateState';
import useHandleError from 'hooks/useHandleError';
import useModal from 'hooks/useModal';
import useSnackbar from 'hooks/useSnackbar';
import validateGroupData from 'pages/Create/validate';
import { groupDetailState, modalState } from 'store/states';

import * as S from './index.styled';
import Inputs from './Inputs';

function GroupEdit() {
  const modalFlag = useRecoilValue(modalState);

  const { setOffModal } = useModal();
  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const queryClient = useQueryClient();

  const resetGroupData = useResetRecoilState(groupDetailState);

  const params = useLocation().pathname.split('/');
  const id = params[2];

  const {
    useNameState,
    useSelectedCategoryState,
    useCapacityState,
    useDateState,
    useScheduleState,
    useDeadlineState,
    useDescriptionState,
    getGroupState,
  } = useCreateState();

  const editGroup = () => {
    const groupData = getGroupState();

    try {
      validateGroupData(groupData);
    } catch (error) {
      if (!(error instanceof Error)) {
        return;
      }

      setMessage(error.message);
      return;
    }

    requestEditGroup(groupData, Number(id))
      .then(() => {
        setOffModal();
        resetGroupData();

        queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);

        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_EDIT_REQUEST);
      })
      .catch(error => {
        handleError(error);
      });
  };

  return (
    <Modal modalState={modalFlag === 'groupEdit'}>
      <S.Container>
        <S.Title>모임 수정</S.Title>
        <Inputs
          useNameState={useNameState}
          useSelectedCategoryState={useSelectedCategoryState}
          useCapacityState={useCapacityState}
          useDateState={useDateState}
          useScheduleState={useScheduleState}
          useDeadlineState={useDeadlineState}
          useDescriptionState={useDescriptionState}
        />
        <S.ButtonContainer>
          <S.EditButton onClick={editGroup}>수정</S.EditButton>
          <S.RevertButton onClick={setOffModal}>취소하기</S.RevertButton>
        </S.ButtonContainer>
      </S.Container>
    </Modal>
  );
}

export default GroupEdit;
