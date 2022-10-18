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

const Input = styled.input`
  width: 100%;
  height: 2rem;
`;

const Button = styled.button`
  width: 100%;
  height: 2.5rem;

  border-radius: 4px;

  font-size: 1.1rem;

  ${({ theme: { colors } }) => `
    background: ${colors.blue001};
    color: ${colors.white001};
  `};
`;

export { Form, Title, Input, Button };
