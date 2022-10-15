import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100%;
`;

const InfoSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: fit-content;
`;

const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 10px;

  padding: 2rem;
`;

export { Container, InfoSection, InfoContainer };
