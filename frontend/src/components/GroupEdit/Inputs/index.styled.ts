import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const showDropdown = keyframes`
  from {
    top: 16.4rem;

    opacity: 0;
  }

  to {
    top: 18.9rem;

    opacity: 1;
  }
`;

const hideDropdown = keyframes`
  from {
    top: 18.9rem;

    opacity: 1;
  }

  to {
    top: 16.4rem;

    opacity: 0;
  }
`;

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  overflow-y: scroll;

  width: 102%;
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 0.5rem;
`;

const Input = styled.input`
  width: 20rem;
  height: 2.5rem;
`;

const CategoryButton = styled.button`
  text-align: left;

  width: 20rem;
  height: 2.5rem;

  border-radius: 8px;

  font-size: 1.2rem;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};

    border: 1px solid ${colors.gray003};
  `}
`;

const CategoryBox = styled.div`
  display: flex;
  flex-direction: column;

  position: absolute;
  top: 18.9rem;

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

const SingleLineContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

const TextArea = styled.textarea`
  width: 40rem;
  height: 15rem;

  border-radius: 8px;

  padding: 0.5rem;

  font-size: 1.2rem;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};

    border: 1px solid ${colors.gray003};
  `}
`;

export {
  InputContainer,
  Label,
  Input,
  CategoryButton,
  CategoryBox,
  CategoryList,
  SingleLineContainer,
  TextArea,
};
