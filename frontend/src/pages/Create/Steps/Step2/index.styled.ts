import styled from '@emotion/styled';

const OptionBox = styled.div`
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 2rem;
  justify-items: center;

  max-width: 43.75rem;
`;

const Button = styled.button`
  width: 5rem;
  height: 5rem;

  border-radius: 0.5rem;

  font-size: 1.2rem;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};

    border: 1px solid ${colors.gray003};

    &.isActive {
      transform: scale(1.1);
      transition: 0.2s;

      background: ${colors.yellow002};
    }
  `}
`;

export { OptionBox, Button };
