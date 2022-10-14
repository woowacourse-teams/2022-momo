import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  width: 90%;
`;

const Title = styled.h3`
  text-align: center;
`;

const Input = styled.input`
  width: 100%;
  height: 2rem;
`;

const Button = styled.button`
  width: 100%;
  height: 3rem;

  border-radius: 5px;

  font-size: 1.1rem;

  ${({ theme: { colors } }) => `
    background: ${colors.blue001};
    color: ${colors.white001};
  `};
`;

export { Container, Title, Input, Button };
