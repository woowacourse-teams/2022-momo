import * as S from './index.styled';

interface DescriptionProps {
  type: 'detail' | 'location';
  children?: React.ReactNode;
}

function Description({ type, children }: DescriptionProps) {
  return (
    <>
      {type === 'detail' ? (
        <S.DescriptionContainer>
          {children || '(설명이 없어요.)'}
        </S.DescriptionContainer>
      ) : (
        <S.LocationContainer>
          {children || '(정해진 장소가 없어요.)'}
        </S.LocationContainer>
      )}
    </>
  );
}

export default Description;
