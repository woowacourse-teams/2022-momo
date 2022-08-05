import styled from '@emotion/styled';

type InfoMessageProps = {
  isValid?: boolean;
};

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2rem;
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
  width: 20rem;
  height: 2rem;

  box-sizing: border-box;
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

  border-radius: 5px;

  background: ${({ theme: { colors } }) => colors.blue001};
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 1.1rem;
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
