import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  position: fixed;

  z-index: 999;

  width: 100%;
  height: 52px;

  backdrop-filter: saturate(150%) blur(5px);

  box-sizing: border-box;
  padding: 0.5rem 2rem;

  ${({ theme: { colors } }) => `
    background: linear-gradient(90deg, ${colors.blue001} 0%, ${colors.green002} 100%);
  `}
`;

const Logo = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Nav = styled.div`
  display: flex;
  align-items: center;
  gap: 2rem;

  font-size: 1.1rem;
  font-weight: 700;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    color: ${colors.black002};

    svg {
      @media only screen and (max-width: ${md}px) {
        display: none;
      }

      fill: ${colors.white001};

      cursor: pointer;
    }
  `}
`;

export { Container, Logo, Nav };
