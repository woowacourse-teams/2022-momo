import styled from '@emotion/styled';

const Form = styled.form`
  display: flex;
  justify-content: center;
  align-items: flex-end;

  width: 100%;
  height: 2.5rem;

  svg {
    min-width: 35px;

    ${({ theme: { breakpoints } }) => `
      @media only screen and (max-width: ${breakpoints.md}px) {
        transform: scale3d(0.8, 0.8, 1);
      }
    `}
  }
`;

const Input = styled.input`
  width: 100%;

  background: none;
  border: none;
  border-radius: 0;

  margin: 0 0.5rem;
  padding-bottom: 0.5rem;

  transition: border 0.3s;

  ${({ theme: { colors, breakpoints } }) => `
    border-bottom: 1px solid ${colors.gray001};

    &:focus {
      border: none;
      border-bottom: 1px solid ${colors.green002};
    }

    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.9rem;
    }
  `}
`;

export { Form, Input };
