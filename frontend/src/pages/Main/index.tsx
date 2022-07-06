import Card from 'components/Card';
import Category from 'components/Category';
import Search from 'components/Search';

import * as S from './index.styled';

const groups = [
  {
    id: 1,
    name: '오늘 끝나고 맥주 드실 분',
    host: {
      id: 1,
      name: '4기 이프',
    },
    categoryId: 1,
    isRegular: false,
    deadline: new Date(),
  },
  {
    id: 2,
    name: '주말에 같이 야구 봐요',
    host: {
      id: 2,
      name: '4기 유세지',
    },
    categoryId: 1,
    isRegular: false,
    deadline: new Date(),
  },
  {
    id: 3,
    name: 'Git 브랜칭 전략 스터디 모집',
    host: {
      id: 3,
      name: '4기 렉스',
    },
    categoryId: 1,
    isRegular: true,
    deadline: new Date(),
  },
  {
    id: 4,
    name: '토르 러브 앤 썬더 달리실 분',
    host: {
      id: 4,
      name: '4기 콤피',
    },
    categoryId: 1,
    isRegular: false,
    deadline: new Date(),
  },
];

function Main() {
  return (
    <>
      <Search />
      <Category />
      <S.Content>
        <S.Heading>이런 모임, 어때요?</S.Heading>
        <S.GroupListBox>
          {groups.map(group => (
            <Card group={group} key={group.id} />
          ))}
        </S.GroupListBox>
      </S.Content>
    </>
  );
}

export default Main;
