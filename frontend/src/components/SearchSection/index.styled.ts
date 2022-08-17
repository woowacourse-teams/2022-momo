import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  gap: 1.25rem;
  position: relative;

  width: 100%;
  height: 15rem;

  padding-bottom: 2.5rem;
`;

const Background = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  object-fit: cover;

  width: 100%;
  height: 100%;

  background: linear-gradient(
    90deg,
    ${({ theme: { colors } }) => colors.blue001}cc 0%,
    ${({ theme: { colors } }) => colors.green002}cc 100%
  );
`;

const Heading = styled.h2`
  color: ${({ theme: { colors } }) => colors.white001};

  font-weight: 600;
  font-size: 1.5rem;
`;

const Form = styled.div`
  display: flex;

  width: 37rem;
  height: 2.5rem;

  filter: drop-shadow(0 0 4px ${({ theme: { colors } }) => colors.gray001});
`;

const Input = styled.input`
  width: 85%;

  border: none;
  border-radius: 10px 0 0 10px;

  padding: 0 0.5rem;

  &:focus {
    border: none;
  }
`;

const Button = styled.button`
  width: 15%;

  background: ${({ theme: { colors } }) => colors.yellow001};
  color: ${({ theme: { colors } }) => colors.white001};

  border: none;
  border-radius: 0 10px 10px 0;

  font-size: 1rem;
  font-weight: 700;
`;

export { Container, Background, Heading, Form, Input, Button };
