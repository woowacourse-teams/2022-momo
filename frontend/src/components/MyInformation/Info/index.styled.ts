import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const kongKong = keyframes`
  0% {
    bottom: 0;
    left: 0;
  }

  20% {
    bottom: 1rem;
    left: 0.2rem;
  }

  30% {
    bottom: 1rem;
    left: 0.3rem;
  }

  50% {
    bottom: 0;
    left: 0.5rem;
  }

  70% {
    bottom: 1rem;
    left: 0.7rem;
  }

  80% {
    bottom: 1rem;
    left: 0.8rem;
  }

  100% {
    bottom: 0;
    left: 1rem;
  }
`;

const Container = styled.div`
  display: flex;
  gap: 3rem;
`;

const LiveBeanBox = styled.div`
  display: flex;
`;

const LiveBean = styled.div`
  position: relative;

  animation: 1.5s ${kongKong} ease-out infinite alternate;

  &.reverse {
    animation: 1.5s ${kongKong} ease-out infinite alternate-reverse;
  }
`;

const Right = styled.div`
  display: flex;
`;

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
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

export {
  Container,
  LiveBeanBox,
  LiveBean,
  Right,
  InputBox,
  Label,
  Input,
  ButtonBox,
  EditButton,
};
