import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.25rem;

  width: 100%;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      padding: 0.5rem 0;
    }

    @media only screen and (min-width: ${breakpoints.md}px) {
      padding: 1rem 0;
    }
  `}
`;

export { Wrapper };
