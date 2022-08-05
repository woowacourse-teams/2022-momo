import React, { useRef, useState } from 'react';

import { useNavigate } from 'react-router-dom';

import { requestCreateGroup } from 'apis/request/group';
import {
  Step1,
  Step2,
  Step3,
  Step4,
  Step5,
  Step6,
  Step7,
  Step8,
} from 'components/Create';
import Navigator from 'components/Create/Navigator';
import { BROWSER_PATH } from 'constants/path';
import useCreateState from 'hooks/useCreateState';
import PageError from 'utils/PageError';

import * as S from './index.styled';
import validateGroupData from './validate';

const totalPage = [
  { number: 1, content: '이름 입력' },
  { number: 2, content: '카테고리 선택' },
  { number: 3, content: '최대 인원 입력' },
  { number: 4, content: '진행 날짜 선택' },
  { number: 5, content: '날짜, 시간대 상세 입력' },
  { number: 6, content: '모집 마감일자 입력' },
  { number: 7, content: '장소 입력' },
  { number: 8, content: '상세 설명 입력' },
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
  } = useCreateState();
  const duration = {
    start: getGroupState().startDate,
    end: getGroupState().endDate,
  };
  const [page, setPage] = useState(1);
  const pageRefs = useRef<Array<HTMLDivElement | null>>([]);
  const navigate = useNavigate();

  const getPageRef = (page: number) => (element: HTMLDivElement | null) => {
    pageRefs.current[page] = element;

    return pageRefs.current[page];
  };

  const gotoAdjacentPage = (direction: 'next' | 'prev') => {
    if (
      (direction === 'next' && page === totalPage.length) ||
      (direction === 'prev' && page === 1)
    )
      return;

    setPage(prevState => {
      const target = prevState + (direction === 'next' ? 1 : -1);

      changeScroll(target);

      return target;
    });
  };

  const pressEnterToNext = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    gotoAdjacentPage('next');
  };

  const changeScroll = (page: number) => {
    pageRefs.current[page]?.scrollIntoView({
      behavior: 'smooth',
    });
  };

  const createNewGroup = () => {
    const groupData = getGroupState();

    try {
      validateGroupData(groupData);
    } catch (error) {
      if (!(error instanceof PageError)) return;

      alert(error.message);

      return;
    }

    requestCreateGroup(groupData)
      .then(groupId => {
        navigate(`${BROWSER_PATH.DETAIL}/${groupId}`);
      })
      .catch(error => {
        alert(error.message);
      });
  };

  return (
    <S.PageContainer>
      <S.ScrollContainer>
        <Step1
          useNameState={useNameState}
          ref={getPageRef(1)}
          pressEnterToNext={pressEnterToNext}
        />
        <Step2
          useSelectedCategoryState={useSelectedCategoryState}
          ref={getPageRef(2)}
          gotoAdjacentPage={gotoAdjacentPage}
        />
        <Step3
          useCapacityState={useCapacityState}
          ref={getPageRef(3)}
          pressEnterToNext={pressEnterToNext}
        />
        <Step4
          useDateState={useDateState}
          ref={getPageRef(4)}
          pressEnterToNext={pressEnterToNext}
        />
        <Step5
          useScheduleState={useScheduleState}
          duration={duration}
          ref={getPageRef(5)}
          pressEnterToNext={pressEnterToNext}
        />
        <Step6
          useDeadlineState={useDeadlineState}
          ref={getPageRef(6)}
          pressEnterToNext={pressEnterToNext}
        />
        <Step7
          useLocationState={useLocationState}
          ref={getPageRef(7)}
          pressEnterToNext={pressEnterToNext}
        />
        <Step8 useDescriptionState={useDescriptionState} ref={getPageRef(8)} />
      </S.ScrollContainer>
      <Navigator
        page={page}
        setPage={setPage}
        totalPage={totalPage}
        changeScroll={changeScroll}
        gotoAdjacentPage={gotoAdjacentPage}
        createNewGroup={createNewGroup}
      />
    </S.PageContainer>
  );
}

export default Create;
