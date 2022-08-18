import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const showDropdown = keyframes`
  from {
    top: 14rem;

    opacity: 0;
  }

  to {
    top: 16.3rem;

    opacity: 1;
  }
`;

const hideDropdown = keyframes`
  from {
    top: 16.3rem;

    opacity: 1;
  }

  to {
    top: 14rem;

    opacity: 0;
  }
`;

const Container = styled.div`
  position: relative;

  overflow-x: hidden;
  overflow-y: scroll;

  width: 55rem;
  height: 40rem;
  padding: 1rem;
`;

const Title = styled.h3`
  text-align: center;

  margin-bottom: 5rem;
`;

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  margin-top: 2rem;
`;

const SingleLineContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 2rem;
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 0.5rem;
`;

const Input = styled.input`
  width: 25rem;
  height: 2rem;

  box-sizing: border-box;
`;

const EditButton = styled.button`
  width: 6rem;
  height: 2.5rem;

  border-radius: 8px;

  background: ${({ theme: { colors } }) => colors.blue001};
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 1.1rem;
`;

const RevertButton = styled.button`
  width: 6rem;
  height: 2.5rem;

  border: 1px solid ${({ theme: { colors } }) => colors.blue001};
  border-radius: 8px;

  background: ${({ theme: { colors } }) => colors.white001};
  color: ${({ theme: { colors } }) => colors.blue001};

  font-size: 1.1rem;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const CategoryButton = styled.button`
  text-align: left;

  width: 25rem;
  height: 2rem;

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 8px;

  background: ${({ theme: { colors } }) => colors.white001};

  font-size: 1.1rem;
`;

const CategoryBox = styled.div`
  display: flex;
  flex-direction: column;

  position: absolute;
  top: 16.3rem;

  z-index: 99;

  ${({ animationTime }: { animationTime: number }) => css`
    animation: ${showDropdown} ${animationTime}ms;

    &.close {
      animation: ${hideDropdown} ${animationTime}ms;
    }
  `};
`;

const CategoryList = styled(CategoryButton)`
  &:first-of-type {
    border-radius: 8px 8px 0 0;
  }

  &:last-child {
    border-radius: 0 0 8px 8px;
  }

  &:not(:first-of-type, :last-child) {
    border-radius: 0;
  }

  &:hover {
    background: ${({ theme: { colors } }) => colors.green002};
  }
`;

const TextArea = styled.textarea`
  width: 98%;
  min-width: 25rem;
  max-width: 98%;
  height: 15rem;

  background: ${({ theme: { colors } }) => colors.white001};

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 0.5rem;

  padding: 0.5rem;

  font-size: 1.2rem;
`;

export {
  Container,
  Title,
  InputContainer,
  SingleLineContainer,
  Label,
  Input,
  EditButton,
  RevertButton,
  ButtonContainer,
  CategoryButton,
  CategoryBox,
  CategoryList,
  TextArea,
};
