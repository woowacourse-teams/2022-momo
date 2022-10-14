import styled from '@emotion/styled';

const DescriptionBox = styled.div`
  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;

  padding: 1rem;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      font-size: 0.9rem;
    }
  `}
`;

export { DescriptionBox };
