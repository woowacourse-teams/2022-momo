import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 2rem;

  width: 100%;
  height: calc(100% - 4rem);

  margin: 0 auto;
`;

const Image = styled.img`
  object-fit: cover;

  width: 100%;
  height: 17.5rem;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 2rem;

  width: 70%;

  margin: 0 auto;
`;

const Title = styled.h2`
  margin-bottom: 2rem;

  font-size: 1.6rem;
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

const InputLineContainer = styled.span`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

const Button = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 2rem;
  height: 2rem;

  background: none;
`;

export {
  Container,
  Image,
  Content,
  Title,
  InputContainer,
  Label,
  Input,
  InputLineContainer,
  Button,
};
