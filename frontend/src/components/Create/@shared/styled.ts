import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 3rem;

  min-width: 100%;
`;

const Heading = styled.h2`
  font-size: 1.5rem;

  span {
    color: ${({ theme: { colors } }) => colors.green001};
  }

  p {
    text-align: center;

    margin-top: 0.5rem;

    color: ${({ theme: { colors } }) => colors.gray002};

    font-size: 1rem;
  }
`;

const LabelContainer = styled.label`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Input = styled.input`
  text-align: center;

  width: 25rem;
  height: 3rem;
`;

export { Container, Heading, LabelContainer, Input };
