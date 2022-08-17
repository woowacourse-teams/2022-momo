import { useEffect, useState } from 'react';

import { useQueryClient } from 'react-query';
import { useRecoilValue, useResetRecoilState } from 'recoil';

import { requestEditGroup } from 'apis/request/group';
import Modal from 'components/@shared/Modal';
import CalendarEditor from 'components/Create/Step5/CalendarEditor';
import { QUERY_KEY } from 'constants/key';
import { GUIDE_MESSAGE } from 'constants/message';
import { GROUP_RULE } from 'constants/rule';
import useClosingState from 'hooks/useClosingState';
import useCreateState from 'hooks/useCreateState';
import useModal from 'hooks/useModal';
import useSnackbar from 'hooks/useSnackbar';
import validateGroupData from 'pages/Create/validate';
import { categoryState, groupDetailState, modalState } from 'store/states';
import { CategoryType } from 'types/data';
import { getNewDateString } from 'utils/date';
import PageError from 'utils/PageError';

import * as S from './index.styled';

const dropdownAnimationTime = 300;

function GroupEdit() {
  const modalFlag = useRecoilValue(modalState);
  const categories = useRecoilValue(categoryState);
  const groupData = useRecoilValue(groupDetailState);
  const resetGroupData = useResetRecoilState(groupDetailState);

  const { setOffModal } = useModal();
  const { setMessage } = useSnackbar();
  const queryClient = useQueryClient();
  const [isShownDropdown, setIsShownDropdown] = useState(false);
  const { isClosing, close } = useClosingState(dropdownAnimationTime, () => {
    setIsShownDropdown(false);
  });
  const params = window.location.pathname.split('/');
  const id = params[2];

  const {
    useNameState,
    useSelectedCategoryState,
    useCapacityState,
    useDateState,
    useScheduleState,
    useDeadlineState,
    useLocationState,
    useDescriptionState,
    getGroupState,
  } = useCreateState();
  const duration = {
    start: getGroupState().startDate,
    end: getGroupState().endDate,
  };

  const { name, setName, dangerouslySetName } = useNameState();
  const { selectedCategory, setSelectedCategory } = useSelectedCategoryState();
  const { capacity, setCapacity } = useCapacityState();
  const {
    startDate,
    setStartDate,
    endDate,
    setEndDate,
    dangerouslySetStartDate,
    dangerouslySetEndDate,
  } = useDateState();
  const { setSchedules } = useScheduleState();
  const { deadline, setDeadline, dangerouslySetDeadline } = useDeadlineState();
  const { location, setLocation, dangerouslySetLocation } = useLocationState();
  const { description, setDescription, dangerouslySetDescription } =
    useDescriptionState();

  useEffect(() => {
    dangerouslySetName(groupData?.name || '');
    setSelectedCategory({
      id: groupData?.categoryId,
      name: categories[groupData.categoryId - 1 || 0]?.name,
    });
    setCapacity(groupData?.capacity || 0);
    dangerouslySetStartDate(groupData?.duration.start || '');
    dangerouslySetEndDate(groupData?.duration.end || '');
    groupData?.schedules.forEach(schedule => setSchedules(schedule));
    dangerouslySetDeadline(groupData?.deadline || '');
    dangerouslySetLocation(groupData?.location || '');
    dangerouslySetDescription(groupData?.description || '');
  }, [modalFlag]);

  if (!groupData) return <></>;

  const toggleDropdownState = () => {
    if (isShownDropdown) {
      close();
      return;
    }

    setIsShownDropdown(true);
  };

  const changeCategory = (item: CategoryType) => () => {
    setSelectedCategory(item);
    close();
  };

  const changeCapacity = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCapacity(Number(e.target.value));
  };

  const groupEdit = () => {
    const groupData = getGroupState();

    try {
      validateGroupData(groupData);
    } catch (error) {
      if (!(error instanceof PageError)) return;

      alert(error.message);

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
        alert(error.message);
      });
  };

  return (
    <Modal modalState={modalFlag === 'groupEdit'}>
      <S.Container>
        <S.Title>모임 수정</S.Title>
        <S.InputContainer>
          {groupData && (
            <>
              <S.Label>
                이름
                <S.Input
                  type="text"
                  placeholder="정체를 밝혀라 @-@"
                  value={name}
                  onChange={setName}
                />
              </S.Label>
              <S.Label>
                카테고리
                <S.CategoryButton onClick={toggleDropdownState}>
                  {selectedCategory.name}
                </S.CategoryButton>
                {isShownDropdown && (
                  <S.CategoryBox
                    className={isClosing ? 'close' : ''}
                    animationTime={dropdownAnimationTime}
                  >
                    {categories &&
                      categories.map(item => (
                        <S.CategoryList
                          onClick={changeCategory(item)}
                          key={item.id}
                        >
                          {item.name}
                        </S.CategoryList>
                      ))}
                  </S.CategoryBox>
                )}
              </S.Label>
              <S.Label>
                최대 인원 수
                <S.Input
                  type="number"
                  min="1"
                  max="99"
                  placeholder="99"
                  value={capacity}
                  onChange={changeCapacity}
                />
              </S.Label>
              <S.Label>
                기간
                <S.SingleLineContainer>
                  <S.Input
                    type="date"
                    value={startDate}
                    onChange={setStartDate}
                    min={getNewDateString('day')}
                  />
                  ~
                  <S.Input
                    type="date"
                    value={endDate}
                    onChange={setEndDate}
                    min={startDate || getNewDateString('day')}
                  />
                </S.SingleLineContainer>
              </S.Label>
              <S.Label>
                상세 일정
                <CalendarEditor
                  useScheduleState={useScheduleState}
                  duration={duration}
                />
              </S.Label>
              <S.Label>
                모집 마감일
                <S.Input
                  type="datetime-local"
                  value={deadline}
                  onChange={setDeadline}
                  min={getNewDateString('min')}
                />
              </S.Label>
              <S.Label>
                장소
                <S.Input type="text" value={location} onChange={setLocation} />
              </S.Label>
              <S.Label>
                설명
                <S.TextArea
                  value={description}
                  onChange={setDescription}
                  maxLength={GROUP_RULE.DESCRIPTION.MAX_LENGTH}
                />
              </S.Label>
              <S.ButtonContainer>
                <S.EditButton onClick={groupEdit}>수정</S.EditButton>
                <S.RevertButton onClick={setOffModal}>취소하기</S.RevertButton>
              </S.ButtonContainer>
            </>
          )}
        </S.InputContainer>
      </S.Container>
    </Modal>
  );
}

export default GroupEdit;
