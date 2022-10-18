import styled from '@emotion/styled';

type InfoMessageProps = {
  isValid?: boolean;
};

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 90%;
`;

const Title = styled.h3`
  text-align: center;
`;

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Input = styled.input`
  width: 100%;
  height: 2rem;
`;

const InfoMessage = styled.span<InfoMessageProps>`
  color: ${({ isValid, theme: { colors } }) =>
    isValid ? colors.gray002 : colors.red002};

  font-size: 0.9rem;
`;

const WarningMessage = styled(InfoMessage)`
  color: ${({ isValid, theme: { colors } }) =>
    isValid ? colors.white001 : colors.red002};
`;

const Button = styled.button`
  height: 3rem;

  border-radius: 4px;

  font-size: 1.1rem;

  ${({ theme: { colors } }) => `
    background: ${colors.blue001};
    color: ${colors.white001};
  `}
`;

export {
  Form,
  Title,
  InputContainer,
  Label,
  Input,
  InfoMessage,
  WarningMessage,
  Button,
};
