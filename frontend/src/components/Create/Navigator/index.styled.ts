import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 12px;

  margin-bottom: 5rem;

  button {
    background: none;
  }
`;

const SideButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 3rem;
  height: 3rem;
`;

export { Container, SideButton };
