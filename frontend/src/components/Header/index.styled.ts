import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: fixed;

  z-index: 999;

  width: 100%;
  height: 4rem;

  backdrop-filter: saturate(150%) blur(5px);

  box-sizing: border-box;
  padding: 0.5rem 2rem;

  ${({ theme: { colors } }) => `
    background: linear-gradient(90deg, ${colors.blue001} 0%, ${colors.green002} 100%);
  `}
`;

const Logo = styled.div`
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 2.5rem;
  font-weight: 900;
`;

const Nav = styled.div`
  display: flex;
  align-items: center;
  gap: 1.5rem;

  color: ${({ theme: { colors } }) => colors.black002};

  font-size: 1.1rem;
  font-weight: 700;

  div {
    cursor: pointer;
  }
`;

export { Container, Logo, Nav };
