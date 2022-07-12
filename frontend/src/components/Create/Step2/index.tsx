import { Container, Heading } from '../@shared/styled';
import * as S from './index.styled';

const categories = [
  '운동',
  '스터디',
  '한 잔',
  '영화',
  '모각코',
  '맛집',
  '카페',
  '쇼핑',
  '등산',
  '문화생활',
];

function Step2() {
  const activeCategory = categories[2];

  return (
    <Container>
      <Heading>
        <span>어떤</span> 모임인가요?
        <p>(카테고리 선택)</p>
      </Heading>
      <S.Options>
        {categories.map(category => (
          <S.Button
            type="button"
            key={category}
            className={activeCategory === category ? 'isActive' : ''}
          >
            {category}
          </S.Button>
        ))}
      </S.Options>
    </Container>
  );
}

export default Step2;
