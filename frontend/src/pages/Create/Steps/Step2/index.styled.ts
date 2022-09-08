import styled from '@emotion/styled';

const Options = styled.div`
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 2rem;
  justify-items: center;

  max-width: 43.75rem;
`;

const Button = styled.button`
  width: 5rem;
  height: 5rem;

  background: ${({ theme: { colors } }) => colors.white001};

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 0.5rem;

  font-size: 1.2rem;

  &.isActive {
    transform: scale(1.1);
    transition: 0.2s;

    background: ${({ theme: { colors } }) => colors.yellow002};
  }
`;

export { Options, Button };
