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
  align-items: center;

  gap: 1rem;
`;

const Button = styled.button`
  width: 20rem;
  height: 3rem;

  border-radius: 5px;

  background: ${({ theme: { colors } }) => colors.blue001};
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 1.1rem;
`;

const SignupButton = styled.div`
  font-size: 1rem;

  color: ${({ theme: { colors } }) => colors.gray002};

  cursor: default;

  span {
    color: ${({ theme: { colors } }) => colors.gray001};

    cursor: pointer;

    &:hover {
      color: ${({ theme: { colors } }) => colors.blue001};
    }
  }
`;

const Divider = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 20rem;
  height: 1px;

  margin-top: 1rem;

  background: ${({ theme: { colors } }) => colors.gray003};

  span {
    padding: 0.2rem 1rem 0 1rem;

    background: ${({ theme: { colors } }) => colors.white001};
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
  SignupButton,
  Divider,
  OAuthButtonWrapper,
  OAuthButton,
};
