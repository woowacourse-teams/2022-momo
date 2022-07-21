import styled from '@emotion/styled';

const Dimmer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0;
  top: 0;

  z-index: 2147483647;

  width: 100%;
  height: 100%;

  background: ${({ theme: { filter } }) => filter.darken001};
  backdrop-filter: saturate(100%) blur(5px);

  transition: all 0.3s ease-in;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  border-radius: 16px;
  padding: 5rem;

  background: ${({ theme: { colors } }) => colors.white001};
  filter: drop-shadow(0 0 4px ${({ theme: { colors } }) => colors.gray001});
`;

export { Dimmer, Content };
