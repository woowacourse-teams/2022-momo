import styled from '@emotion/styled';

const Container = styled.div`
  position: relative;
`;

const Modal = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 2rem;
  left: -4rem;

  z-index: 999;

  width: 10rem;
  height: 2rem;

  border-radius: 20px;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};
    color: ${colors.gray001};
    filter: drop-shadow(0 0 1px ${colors.gray001});
  `}
`;

export { Container, Modal };
