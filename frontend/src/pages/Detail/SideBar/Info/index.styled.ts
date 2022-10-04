import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;

  min-width: max-content;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;
`;

const Content = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;

  padding: 1rem 1.5rem;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;

  min-height: 3rem;
`;

const EditWrapper = styled(Wrapper)`
  justify-content: space-between;

  padding-right: 1.5rem;

  div {
    display: flex;
    align-items: center;
  }

  & > svg {
    cursor: pointer;

    &:hover {
      fill: ${({ theme: { colors } }) => colors.gray001};
    }
  }
`;

const Text = styled.span`
  color: ${({ theme: { colors } }) => colors.black002};

  margin-left: 1rem;

  font-weight: 700;
`;

export { Container, Content, Wrapper, EditWrapper, Text };
