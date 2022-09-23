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

  max-width: 40rem;
`;

const TextArea = styled.textarea`
  width: 90%;
  max-width: 40rem;
  height: 15rem;

  border-radius: 0.5rem;

  padding: 0.5rem;

  font-size: 1.2rem;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};

    border: 1px solid ${colors.gray003};
  `}
`;

export { LabelContainer, Label, TextArea };
