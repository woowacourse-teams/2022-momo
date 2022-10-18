import styled from '@emotion/styled';

const LabelContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
`;

const Label = styled.div`
  display: flex;
  justify-content: flex-end;

  width: 100%;
`;

const TextArea = styled.textarea`
  resize: vertical;

  width: 100%;
  height: 15rem;

  border-radius: 0.5rem;

  box-sizing: border-box;
  padding: 0.5rem;

  font-size: 1.2rem;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    background: ${colors.white001};

    border: 1px solid ${colors.gray003};

    @media only screen and (max-width: ${md}px) {
      font-size: 1rem;
    }
  `}
`;

export { LabelContainer, Label, TextArea };
