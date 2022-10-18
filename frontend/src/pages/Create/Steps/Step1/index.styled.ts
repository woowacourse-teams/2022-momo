import styled from '@emotion/styled';

const OptionBox = styled.select`
  width: fit-content;

  border: none;
  outline: none;
`;

const Button = styled.button`
  width: 5rem;
  height: 5rem;

  border-radius: 0.5rem;

  font-size: 1.2rem;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};

    border: 1px solid ${colors.gray003};
  `}
`;

export { OptionBox, Button };
