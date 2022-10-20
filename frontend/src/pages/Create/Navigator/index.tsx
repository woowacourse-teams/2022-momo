import { Dispatch, SetStateAction, useState } from 'react';

import { ExclamationMarkSVG, TriangleSVG } from 'assets/svg';
import { PageType } from 'types/data';

import * as S from './index.styled';

interface NavigatorProps {
  page: number;
  setPage: Dispatch<SetStateAction<number>>;
  totalPage: PageType[];
  getValidateState: (pageIndex: number) => '' | 'invalid';
}

function Navigator({
  page,
  setPage,
  totalPage,
  getValidateState,
}: NavigatorProps) {
  const [isClosed, setIsClosed] = useState(true);

  const changePage = (newPageNumber: number) => () => {
    setPage(newPageNumber);
    setIsClosed(true);
    window.scroll({ top: 0, behavior: 'smooth' });
  };

  const changeCloseState = () => {
    setIsClosed(prevState => !prevState);
  };

  const getPageContentBoxClassName = () => {
    const currentStep = `step-${page}`;
    return isClosed ? `closed ${currentStep}` : '';
  };

  return (
    <S.Container>
      <S.PageContentsBox className={getPageContentBoxClassName()}>
        {totalPage.map((page, pageIndex) => (
          <S.PageItem key={page.content} onClick={changePage(page.number)}>
            <S.Classification
              className={page.required ? 'required' : 'not-required'}
            >
              {page.required ? '필수' : '선택'}
            </S.Classification>
            <S.Content>{page.content}</S.Content>
            <S.Required>
              {page.required && (
                <ExclamationMarkSVG
                  className={getValidateState(pageIndex + 1)}
                />
              )}
            </S.Required>
          </S.PageItem>
        ))}
      </S.PageContentsBox>
      <S.ToggleButton
        className={isClosed ? 'closed' : ''}
        onClick={changeCloseState}
      >
        <TriangleSVG />
      </S.ToggleButton>
    </S.Container>
  );
}

export default Navigator;
