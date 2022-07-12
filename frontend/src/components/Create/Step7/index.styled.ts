import styled from '@emotion/styled';

const TextArea = styled.textarea`
  width: 90%;
  max-width: 40rem;
  height: 15rem;

  background: ${({ theme: { colors } }) => colors.white001};

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 0.5rem;

  padding: 0.5rem;

  font-size: 1.2rem;
`;

export { TextArea };
