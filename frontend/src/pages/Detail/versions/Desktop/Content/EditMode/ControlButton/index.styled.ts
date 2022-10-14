import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Button = styled.button`
  width: 47%;
  height: 3rem;

  border-radius: 50px;

  font-size: 1.2rem;
  font-weight: 700;
`;

const EditButton = styled(Button)`
  ${({ theme: { colors } }) => `
    background: linear-gradient(
      180deg,
      ${colors.green002}99 0%,
      ${colors.green002} 20%
    );
    color: ${colors.white001};
  `}
`;

const CancelButton = styled(Button)`
  ${({ theme: { colors } }) => `
    background: linear-gradient(
      180deg,
      ${colors.gray002}99 0%,
      ${colors.gray002} 20%
    );
    color: ${colors.white001};
  `}
`;

export { Container, EditButton, CancelButton };
