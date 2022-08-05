import styled from '@emotion/styled';

const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 2rem;

  height: calc(100% - 4rem);
`;

const ScrollContainer = styled.div`
  display: flex;
  align-items: center;
  overflow: hidden;
  scroll-behavior: smooth;

  width: 100%;
  min-height: fit-content;

  div {
    width: 100%;
  }

  ::-webkit-scrollbar {
    display: none;
  }
`;

export { PageContainer, ScrollContainer };
