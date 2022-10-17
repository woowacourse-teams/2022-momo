import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;

  position: fixed;
  bottom: 0;

  z-index: 100;

  width: 100%;
  height: 52px;

  a {
    width: 100%;
  }

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    background: ${colors.white001};
    border-top: 1px solid ${colors.gray005};

    @media only screen and (min-width: ${md + 1}px) {
      display: none;
    }
  `}
`;

const Button = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.1rem;

  width: 100%;

  background: none;

  font-size: 0.8rem;

  ${({ theme: { colors } }) => `
    color: ${colors.gray001};

    svg > path {
      fill: ${colors.gray002};
    }
  `}
`;

export { Container, Button };
