import styled from '@emotion/styled';

const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  padding-bottom: 60px;
`;

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.25rem;

  padding: 2rem 0;
`;

const NextPageButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.25rem;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    &.invalid {
      color: ${colors.gray004};

      svg > g > path {
        fill: ${colors.gray004};
      }
    }

    &.last-page {
      display: none;
    }
    
    @media only screen and (min-width: ${md}px) {
      h3 {
        font-size: 1.2rem;
      }

      svg {
        width: 1.875rem;
        height: 1.875rem;
      }
    }
  `}
`;

const SubmitButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.25rem;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    span {
      color: ${colors.green001};
    }

    svg > circle {
      fill: ${colors.green001};
    }

    &.invalid {
      color: ${colors.gray004};

      span {
        color: ${colors.gray005};
      }

      svg > circle {
        fill: ${colors.gray004};
      }
    }

    @media only screen and (min-width: ${md}px) {
      h3 {
        font-size: 1.2rem;
      }

      svg {
        width: 1.875rem;
        height: 1.875rem;
      }
    }
  `}
`;

export { PageContainer, ButtonContainer, NextPageButton, SubmitButton };
