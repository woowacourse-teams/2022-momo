import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 2rem;

  width: 100%;

  margin: auto;

  ${({ theme: { breakpoints } }) => `
    max-width: ${breakpoints.md}px;
  `}
`;

const Heading = styled.h2`
  margin-left: 1rem;

  font-size: 1.5rem;
  font-weight: 300;
`;

const GroupListBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;

  a {
    width: 96%;
  }
`;

const LoadingWrapper = styled.div`
  display: flex;
  justify-content: center;

  width: 100%;
`;

export { Container, Heading, GroupListBox, LoadingWrapper };
