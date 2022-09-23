import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.5rem;

  height: 30rem;
`;

const Title = styled.h3`
  text-align: center;

  font-size: 1.2rem;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Button = styled.button`
  width: 6rem;
  height: 2.5rem;

  border-radius: 8px;

  font-size: 1.1rem;
`;

const EditButton = styled(Button)`
  ${({ theme: { colors } }) => `
    background: ${colors.blue001};
    color: ${colors.white001};
  `}
`;

const RevertButton = styled(Button)`
  ${({ theme: { colors } }) => `
    background: ${colors.white001};
    color: ${colors.blue001};

    border: 1px solid ${colors.blue001};
  `}
`;

export { Container, Title, ButtonContainer, EditButton, RevertButton };
