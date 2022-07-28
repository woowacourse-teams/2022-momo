import styled from '@emotion/styled';

const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 4rem;

  height: calc(100% - 4rem);
`;

const PageTitle = styled.h1`
  font-size: 2rem;
`;

const PageDescription = styled.p`
  font-size: 1.2rem;
`;

export { PageContainer, PageTitle, PageDescription };
