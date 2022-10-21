import { useState } from 'react';

import { useNavigate } from 'react-router-dom';

import { requestCreateGroup } from 'apis/request/group';
import { CompleteSVG } from 'assets/svg';
import RightArrow from 'components/svg/RightArrow';
import { BROWSER_PATH } from 'constants/path';
import useCreateState from 'hooks/useCreateState';
import Navigator from 'pages/Create/Navigator';
import theme from 'styles/theme';

import * as S from './index.styled';
import CreateSteps from './Steps/CreateSteps';
import { validateGroupData } from './validate';

const svgSize = 20;

const totalPage = [
  { number: 1, content: '이름 입력 / 카테고리 선택', required: true },
  { number: 2, content: '기간 입력 / 마감일 입력', required: true },
  { number: 3, content: '일정 입력', required: false },
  { number: 4, content: '장소 입력 / 최대 인원 입력', required: false },
  { number: 5, content: '설명 입력', required: false },
];

function Create() {
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
    isEmptyInput,
  } = useCreateState();
  const duration = {
    start: getGroupState().startDate,
    end: getGroupState().endDate,
  };
  const [page, setPage] = useState(1);
  const navigate = useNavigate();

  const gotoNextPage = () => {
    if (page >= totalPage.length) {
      return;
    }

    setPage(prevPage => prevPage + 1);
    window.scroll({ top: 0, behavior: 'smooth' });
  };

  const pressEnterToNext = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    gotoNextPage();
  };

  const createNewGroup = () => {
    const groupData = getGroupState();

    try {
      validateGroupData(groupData);
    } catch (error) {
      if (error instanceof Error) {
        alert(error.message);
        return;
      }
    }

    requestCreateGroup(groupData)
      .then(groupId => {
        navigate(`${BROWSER_PATH.DETAIL}/${groupId}`);
      })
      .catch(error => {
        alert(error.message);
      });
  };

  const getValidateState = (pageIndex: number) => {
    switch (pageIndex) {
      case 1:
        if (!isEmptyInput.name) return 'invalid';
        break;
      case 2:
        if (
          !(
            isEmptyInput.startDate &&
            isEmptyInput.endDate &&
            isEmptyInput.deadline
          )
        )
          return 'invalid';
    }
    return '';
  };

  const getSubmitAvailableState = () => {
    return (
      isEmptyInput.name &&
      isEmptyInput.startDate &&
      isEmptyInput.endDate &&
      isEmptyInput.deadline
    );
  };

  return (
    <S.PageContainer>
      <Navigator
        page={page}
        setPage={setPage}
        totalPage={totalPage}
        getValidateState={getValidateState}
      />
      <CreateSteps
        useNameState={useNameState}
        useSelectedCategoryState={useSelectedCategoryState}
        useCapacityState={useCapacityState}
        useDateState={useDateState}
        useScheduleState={useScheduleState}
        useDeadlineState={useDeadlineState}
        useLocationState={useLocationState}
        useDescriptionState={useDescriptionState}
        pressEnterToNext={pressEnterToNext}
        gotoNextPage={gotoNextPage}
        getValidateState={getValidateState}
        duration={duration}
        page={page}
      />
      <S.ButtonContainer>
        <S.NextPageButton
          onClick={gotoNextPage}
          disabled={getValidateState(page) === 'invalid'}
          className={`${getValidateState(page)} ${
            page >= totalPage.length ? `last-page` : ''
          }`}
        >
          <h3>다음</h3>
          <RightArrow width={svgSize} color={theme.colors.green001} />{' '}
        </S.NextPageButton>
        <S.SubmitButton
          onClick={createNewGroup}
          disabled={!getSubmitAvailableState()}
          className={getSubmitAvailableState() ? '' : 'invalid'}
        >
          <CompleteSVG width={svgSize} />{' '}
          <h3>
            이대로 <span>모임</span>을 생성할게요
          </h3>
        </S.SubmitButton>
      </S.ButtonContainer>
    </S.PageContainer>
  );
}

export default Create;
