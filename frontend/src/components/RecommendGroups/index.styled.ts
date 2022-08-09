import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Heading = styled.h2`
  padding-left: 1rem;

  font-size: 1.5rem;
  font-weight: 300;
`;

const GroupListBox = styled.div`
  display: grid;
  place-items: center;
  row-gap: 3rem;

  width: 100%;

  ${({ theme: { breakpoints } }) => css`
    @media only screen and (max-width: ${breakpoints.md}px) {
      grid-template-columns: repeat(1, 1fr);
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      grid-template-columns: repeat(2, 1fr);
    }

    @media only screen and (min-width: ${breakpoints.lg}px) and (max-width: ${breakpoints.xl}px) {
      grid-template-columns: repeat(3, 1fr);
    }

    @media only screen and (min-width: ${breakpoints.xl}px) {
      grid-template-columns: repeat(4, 1fr);
    }
  `}
`;

const NoResultContainer = styled.div`
  display: flex;
  justify-content: center;
  text-align: center;

  width: 100%;

  margin-top: 5rem;
`;

const NoResultWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;

  width: 50rem;
`;

const NoResultDescription = styled.h3`
  line-height: 2.5rem;

  font-size: 1.3rem;
`;

const LoadingWrapper = styled.div`
  display: flex;
  justify-content: center;

  width: 100%;
`;

export {
  Heading,
  GroupListBox,
  NoResultContainer,
  NoResultWrapper,
  NoResultDescription,
  LoadingWrapper,
};
