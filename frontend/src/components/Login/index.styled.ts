import styled from '@emotion/styled';

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

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
`;

const Button = styled.button`
  width: 100%;
  height: 3rem;

  border-radius: 4px;

  font-size: 1.1rem;

  ${({ theme: { colors } }) => `
    background: ${colors.blue001};
    color: ${colors.white001};
  `};
`;

const SignupButton = styled.div`
  font-size: 1rem;

  cursor: default;

  ${({ theme: { colors } }) => `
    color: ${colors.gray002};

    span {
      color: ${colors.gray001};

      cursor: pointer;

      &:hover {
        color: ${colors.blue001};
      }
    }
  `}
`;

const Divider = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 1px;

  margin-top: 1rem;

  ${({ theme: { colors } }) => `
    background: ${colors.gray003};

    span {
      padding: 0.2rem 1rem 0 1rem;

      background: ${colors.white001};
    }
  `}
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
  aspect-ratio: 1 / 1;

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
