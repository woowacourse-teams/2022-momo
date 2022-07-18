import { useQuery } from 'react-query';

import { getGroups } from 'apis/request/group';
import Card from 'components/Card';
import Category from 'components/Category';
import Search from 'components/Search';
import { QUERY_KEY } from 'constants/key';

import * as S from './index.styled';

function Main() {
  const { data, isLoading, isError } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    getGroups,
  );

  if (isLoading) return <h2>잠시만 기다려주세요... 🔎</h2>;

  if (isError) return <h2>에러가 발생했습니다!!!! 👿</h2>;

  return (
    <>
      <Search />
      <Category />
      <S.Content>
        <S.Heading>이런 모임, 어때요?</S.Heading>
        <S.GroupListBox>
          {data?.map(group => (
            <Card group={group} key={group.id} />
          ))}
        </S.GroupListBox>
      </S.Content>
    </>
  );
}

export default Main;
