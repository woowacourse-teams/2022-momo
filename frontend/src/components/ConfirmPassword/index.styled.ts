import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Title = styled.h3`
  text-align: center;
`;

const Input = styled.input`
  width: 20rem;
  height: 2rem;

  box-sizing: border-box;
`;

const Button = styled.button`
  width: 20rem;
  height: 3rem;

  border-radius: 5px;

  background: ${({ theme: { colors } }) => colors.blue001};
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 1.1rem;
`;

export { Container, Title, Input, Button };
