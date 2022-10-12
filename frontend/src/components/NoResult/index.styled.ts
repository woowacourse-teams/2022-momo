import styled from '@emotion/styled';

const NoResultContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 3rem;

  width: 100%;

  margin-top: 2rem;
`;

const NoResultDescription = styled.h3`
  ${({
    theme: {
      breakpoints: { md, lg },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      line-height: 1.5rem;

      font-size: 1rem;
    }

    @media only screen and (min-width: ${md + 1}px) and (max-width: ${lg}px) {
      line-height: 2rem;

      font-size: 1.1rem;
    }

    @media only screen and (min-width: ${lg}px) {
      line-height: 2.5rem;

      font-size: 1.3rem;
    }
  `}
`;

export { NoResultContainer, NoResultDescription };
