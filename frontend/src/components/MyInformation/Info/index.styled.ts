import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  gap: 3rem;
`;

const Right = styled.div`
  display: flex;
`;

const InputBox = styled.div`
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

const ButtonBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  gap: 1.5rem;

  margin-bottom: 0.2rem;
`;

const EditButton = styled.button`
  background: none;
`;

export { Container, Right, InputBox, Label, Input, ButtonBox, EditButton };
