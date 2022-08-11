import styled from '@emotion/styled';

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

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;

  gap: 1rem;
`;

const Button = styled.button`
  height: 3rem;

  border-radius: 5px;

  background: ${({ theme: { colors } }) => colors.blue001};
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 1.1rem;
`;

const Divider = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 1px;

  background: ${({ theme: { colors } }) => colors.gray003};

  margin-top: 1rem;

  span {
    background: ${({ theme: { colors } }) => colors.white001};

    padding: 0.2rem 1rem 0 1rem;
  }
`;

const OAuthButtonWrapper = styled.div`
  display: flex;
  justify-content: center;

  padding-top: 1rem;
`;

const OAuthButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 3rem;
  height: 3rem;

  border-radius: 50%;
`;

export {
  Form,
  Title,
  InputContainer,
  Label,
  Input,
  ButtonContainer,
  Button,
  Divider,
  OAuthButtonWrapper,
  OAuthButton,
};
