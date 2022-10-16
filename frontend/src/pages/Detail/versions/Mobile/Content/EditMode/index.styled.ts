import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
`;

const Input = styled.input`
  padding: 0.2rem;

  font-size: 1rem;
`;

const Category = styled.select`
  width: fit-content;

  border: none;
  outline: none;
`;

const Title = styled(Input)`
  width: 90%;

  font-size: 1.4rem;
`;

const Deadline = styled(Input)`
  width: fit-content;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      font-size: 0.9rem;
    }
  `}
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  padding: 14rem 1rem 2rem;
`;

const Description = styled.textarea`
  resize: vertical;

  height: 5rem;
  line-height: 1.5rem;

  border-radius: 10px;
  padding: 1rem;

  font-size: 1.1rem;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    color: ${colors.black002};
    border: 1px solid ${colors.gray002};

    @media only screen and (max-width: ${md}px) {
      font-size: 1rem;
    }
  `}
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Duration = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

export {
  Container,
  Input,
  Category,
  Title,
  Deadline,
  ContentContainer,
  Description,
  Label,
  Duration,
};
export { StickyContainer, Header, TitleContainer } from '../index.styled';
