import styled from '@emotion/styled';

const DotWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 12px;
  height: 12px;
`;

const Dot = styled.div`
  width: 12px;
  height: 12px;

  border-radius: 50%;

  background: ${({ color }) => color};
`;

export { DotWrapper, Dot };
