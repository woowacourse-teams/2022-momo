import styled from '@emotion/styled';

const ImageBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 120%;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.sm}px) {
      flex-direction: column;

      max-width: 320px;

      img {
        position: absolute;

        width: 12rem;

        &:first-of-type {
          top: 20%;
          left: calc(40% - 120px);
        }

        &:nth-of-type(2) {
          top: 25%;
          right: calc(40% - 120px);
          
          animation-delay: 0.3s;
        }

        &:last-of-type {
          width: 18rem;

          top: 35%;

          animation-delay: 0.6s;
        }
      }
    }
  
    @media only screen and (min-width: ${breakpoints.sm}px) and (max-width: ${breakpoints.md}px) {
      flex-direction: column;

      width: 425px;

      img {
        position: absolute;

        width: 18rem;

        &:first-of-type {
          top: 25%;
          left: calc(40% - 160px);
        }

        &:nth-of-type(2) {
          top: 35%;
          right: calc(40% - 160px);

          animation-delay: 0.3s;
        }

        &:last-of-type {
          width: 20rem;

          top: 45%;

          animation-delay: 0.6s;
        }
      }
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      flex-direction: column;

      width: 768px;

      img {
        position: absolute;

        width: 20rem;

        &:first-of-type {
          top: 25%;
          left: calc(40% - 200px);
        }

        &:nth-of-type(2) {
          top: 35%;
          right: calc(40% - 200px);

          animation-delay: 0.3s;
        }

        &:last-of-type {
          width: 25rem;

          top: 45%;

          animation-delay: 0.6s;
        }
      }
    }

    @media only screen and (min-width: ${breakpoints.lg}px) {
      img {
        &:first-of-type {
          margin-right: -30rem;
        }
    
        &:nth-of-type(2) {
          animation-delay: 0.3s;
        }
    
        &:last-of-type {
          margin-left: -30rem;
          animation-delay: 0.6s;
        }
      }
    }
  `}
`;

export { ImageBox };
