import styled from '@emotion/styled';

const Label = styled.label`
  display: flex;
  gap: 0.5rem;

  cursor: pointer;

  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
`;

const Checkbox = styled.div`
  display: flex;
  justify-content: center;

  width: 1rem;
  height: 1rem;

  border: 1px solid ${({ theme: { colors } }) => colors.green001};
  border-radius: 4px;

  &.checked {
    background: ${({ theme: { colors } }) => colors.green001};
  }
`;

const Check = styled.div`
  transform: rotate(-45deg);

  width: 0.5rem;
  height: 0.3rem;

  border-bottom: 2px solid ${({ theme: { colors } }) => colors.white001};
  border-left: 2px solid ${({ theme: { colors } }) => colors.white001};
  margin-top: 0.2rem;
`;

export { Label, Checkbox, Check };
