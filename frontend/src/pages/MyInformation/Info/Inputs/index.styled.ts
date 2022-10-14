import styled from '@emotion/styled';

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  align-items: center;
  gap: 0.5rem;

  white-space: nowrap;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.8rem;
    }
  `};
`;

const Input = styled.input`
  width: 80%;
  max-width: 15rem;
  height: 2rem;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 1rem;
    }
  `};
`;

export { InputBox, Label, Input };
