import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 100%;

  box-sizing: border-box;
  padding: calc(3rem + 52px) 1rem 2rem;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) => `
    @media only screen and (min-width: ${md}px) {
      gap: 5rem;

      width: ${md}px;

      padding: calc(5rem + 52px) 1rem;
    }
  `}
`;

const SectionContainer = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  ${({ theme: { colors } }) => `
    & > span {
      font-size: 0.9rem;
      
      color: ${colors.gray004};

      &.invalid {
        color: ${colors.red001};
      }
    }
  `}
`;

const Heading = styled.h2`
  font-size: 1.5rem;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    span {
      color: ${colors.green002};
    }

    p {
      margin-top: 0.5rem;

      color: ${colors.gray002};

      font-size: 1rem;
    }

    @media only screen and (max-width: ${md}px) {
      font-size: 1rem;

      p {
        font-size: 0.8rem;
      }
    }
  `}
`;

const LabelContainer = styled.label`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Label = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Input = styled.input`
  text-align: center;

  width: 100%;
  height: 3rem;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      font-size: 1rem;
    }
  `}
`;

const ErrorColor = styled.b`
  color: ${({ theme: { colors } }) => colors.red003};
`;

const WarningColor = styled.b`
  color: ${({ theme: { colors } }) => colors.yellow002};
`;

const SuccessColor = styled.b`
  color: ${({ theme: { colors } }) => colors.green002};
`;

export {
  Container,
  SectionContainer,
  Heading,
  LabelContainer,
  Label,
  Input,
  ErrorColor,
  WarningColor,
  SuccessColor,
};
