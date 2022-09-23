import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 3rem;

  min-width: 100%;
`;

const Heading = styled.h2`
  font-size: 1.5rem;

  ${({ theme: { colors } }) => `
    span {
      color: ${colors.green002};
    }

    p {
      text-align: center;

      margin-top: 0.5rem;

      color: ${colors.gray002};

      font-size: 1rem;
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

  width: 25rem;
  height: 3rem;
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
  Heading,
  LabelContainer,
  Label,
  Input,
  ErrorColor,
  WarningColor,
  SuccessColor,
};
