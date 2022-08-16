import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100%;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: fit-content;
`;

const MyInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 10px;
  padding: 2rem;
`;

const MyInfo = styled.div`
  display: flex;
  gap: 3rem;
`;

const Right = styled.div`
  display: flex;
`;

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

const Input = styled.input`
  width: 15rem;
  height: 2rem;

  box-sizing: border-box;
`;

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  gap: 1.5rem;

  margin-bottom: 0.2rem;
`;

const EditButton = styled.button`
  background: none;
`;

const WithdrawalWrapper = styled.div`
  display: flex;
  justify-content: flex-end;

  width: 100%;
`;

const WithdrawalButton = styled.button`
  width: 5rem;
  height: 2.5rem;

  border-radius: 8px;

  background: ${({ theme: { colors } }) => colors.red001};
  color: ${({ theme: { colors } }) => colors.white001};
`;

export {
  Container,
  Wrapper,
  MyInfoContainer,
  MyInfo,
  Right,
  InputContainer,
  Label,
  Input,
  ButtonContainer,
  EditButton,
  WithdrawalWrapper,
  WithdrawalButton,
};
