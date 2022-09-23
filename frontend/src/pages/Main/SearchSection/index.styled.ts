import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  gap: 1.25rem;
  position: relative;

  width: 100%;
  height: 15rem;

  padding-bottom: 2.5rem;
`;

const Background = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  object-fit: cover;

  width: 100%;
  height: 100%;

  ${({ theme: { colors } }) => `
    background: linear-gradient(
      90deg,
      ${colors.blue001}cc 0%,
      ${colors.green002}cc 100%
    );
  `}
`;

const Heading = styled.h2`
  color: ${({ theme: { colors } }) => colors.white001};

  font-weight: 600;
  font-size: 1.5rem;
`;

export { Container, Background, Heading };
