import { useState } from 'react';

import ErrorBoundary from 'components/ErrorBoundary';
import JoinedGroups from 'components/JoinedGroups';
import NoResult from 'components/NoResult';
import SearchForm from 'components/SearchSection/SearchForm';

import * as S from './index.styled';

const groupTypes = [
  { name: '내가 참여한 모임' },
  { name: '내가 주최한 모임' },
  { name: '내가 찜한 모임' },
];

function RenderSelectedGroupType(groupType: number) {
  switch (groupType) {
    case 0:
      return <JoinedGroups />;
    // case 1:
    //   return <HostedGroups />;
    // case 2:
    //   return <ZzimhanGroups />;
    default:
      return <NoResult>해당 페이지는 준비중이에요 ・゜・(ノД`)</NoResult>;
  }
}

function MyGroup() {
  const [selectedGroupType, setSelectedGroupType] = useState(0);

  const changeSelectedGroupType = (index: number) => {
    setSelectedGroupType(index);
  };

  return (
    <>
      <S.SearchWrapper>
        <SearchForm />
      </S.SearchWrapper>
      <S.GroupTypeBox>
        {groupTypes.map((groupType, i) => (
          <S.Button
            key={groupType.name}
            className={selectedGroupType === i ? 'selected' : ''}
            onClick={() => changeSelectedGroupType(i)}
          >
            {groupType.name}
          </S.Button>
        ))}
      </S.GroupTypeBox>
      <S.Content>
        <ErrorBoundary>
          {RenderSelectedGroupType(selectedGroupType)}
        </ErrorBoundary>
      </S.Content>
    </>
  );
}

export default MyGroup;
