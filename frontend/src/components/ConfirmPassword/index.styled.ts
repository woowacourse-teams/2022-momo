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
`;

const Button = styled.button`
  width: 20rem;
  height: 3rem;

  border-radius: 5px;

  font-size: 1.1rem;

  ${({ theme: { colors } }) => `
    background: ${colors.blue001};
    color: ${colors.white001};
  `};
`;

export { Container, Title, Input, Button };
