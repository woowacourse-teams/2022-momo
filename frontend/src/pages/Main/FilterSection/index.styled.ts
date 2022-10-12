import styled from '@emotion/styled';

const Container = styled.div`
  position: fixed;
  top: 4rem;

  width: 100%;

  z-index: 998;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};
  `}
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  width: 94%;

  margin: 0 auto;
  padding: 0 3%;

  ${({ theme: { breakpoints } }) => `
    max-width: ${breakpoints.md}px;
  `}
`;

export { Container, ContentContainer };
