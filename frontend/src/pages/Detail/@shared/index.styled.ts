import styled from '@emotion/styled';

const DescriptionBox = styled.div`
  width: 95%;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;

  padding: 1rem;
`;

export { DescriptionBox };
