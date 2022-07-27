import styled from '@emotion/styled';

type Size = 'medium' | 'large';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  transform: scale(
    ${({ size }: { size: Size }) => (size === 'medium' ? 0.5 : 1)}
  );

  width: 25rem;
  height: 25rem;

  font-size: ${({ size }: { size: Size }) =>
    size === 'medium' ? '1.8rem' : '1.1rem'};

  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
`;

const Navigator = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  font-size: ${({ size }: { size: Size }) =>
    size === 'medium' ? '2rem' : '1.3rem'};
`;

const Arrow = styled.div`
  cursor: pointer;
`;

const Content = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  place-items: center;
  text-align: center;
  gap: 1rem;
`;

const Day = styled.div``;

const PrevNextDate = styled.div`
  color: ${({ theme: { colors } }) => colors.gray001};
`;

const Date = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 2rem;
  height: 2rem;

  border-radius: 50%;

  cursor: pointer;

  &:hover {
    background: ${({ theme: { colors } }) => colors.yellow002};
  }
`;

export { Container, Navigator, Arrow, Content, Day, PrevNextDate, Date };
