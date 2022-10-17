import styled from '@emotion/styled';

const Container = styled.div`
  position: fixed;
  top: 52px;

  width: 100%;

  background: ${({ theme: { colors } }) => colors.white001};

  z-index: 998;
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  width: 94%;
  max-width: ${({ theme: { breakpoints } }) => breakpoints.md}px;

  margin: 0 auto;
  padding: 0 3%;
`;

export { Container, ContentContainer };
