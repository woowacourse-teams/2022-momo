import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 4rem;

  height: calc(100% - 52px);

  padding: 0 2rem 2rem;
`;

const Title = styled.h1`
  font-size: 2rem;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;

  white-space: nowrap;

  font-size: 1.2rem;
`;

export { Container, Title, Description };
