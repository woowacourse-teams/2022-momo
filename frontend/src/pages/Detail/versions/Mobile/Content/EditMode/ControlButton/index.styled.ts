import styled from '@emotion/styled';

const Container = styled.div`
  position: fixed;
  bottom: 52px;

  width: 100%;

  z-index: 100;
`;

const Button = styled.button`
  width: 50%;
  height: 3rem;

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
