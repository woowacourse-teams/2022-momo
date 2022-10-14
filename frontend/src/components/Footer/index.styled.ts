import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;

  position: fixed;
  bottom: 0;

  width: 100%;
  height: 60px;

  a {
    width: 100%;
  }

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    border-top: 1px solid ${colors.gray005};

    background: ${colors.white001};

    @media only screen and (min-width: ${md}px) {
      display: none;
    }
  `}
`;

const Button = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 100%;

  font-size: 0.9rem;

  background: none;

  ${({ theme: { colors } }) => `
    color: ${colors.gray001};

    svg > path {
      fill: ${colors.gray002};
    }
  `}
`;

export { Container, Button };
