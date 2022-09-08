import styled from '@emotion/styled';

const NoResultContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 3rem;

  width: 100%;

  margin-top: 5rem;
`;

const NoResultDescription = styled.h3`
  line-height: 2.5rem;

  font-size: 1.3rem;
`;

export { NoResultContainer, NoResultDescription };
