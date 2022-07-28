import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: calc(100% - 4rem);

  background: ${({ theme: { colors } }) => colors.gray004};
`;

export { Wrapper };
