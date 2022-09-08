import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const showDropdown = keyframes`
  from {
    top: 2rem;

    opacity: 0;
  }

  to {
    top: 4rem;

    opacity: 1;
  }
`;

const hideDropdown = keyframes`
  from {
    top: 4rem;

    opacity: 1;
  }

  to {
    top: 2rem;

    opacity: 0;
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const Profile = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  ${({ width }: { width: string }) => `
    width: ${width};
    height: ${width};
  `}

  border-radius: 50%;

  background: ${({ theme: { colors } }) => colors.gray004};
`;

const ToggleButton = styled.button`
  width: fit-content;
  height: fit-content;

  border-radius: 50%;

  background: none;
`;

const Dropdown = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;

  position: absolute;
  top: 4rem;
  right: 2rem;

  width: 10rem;

  border-radius: 4px;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};
    filter: drop-shadow(0 0 1.5px ${colors.gray001});

    > div:not(:last-child) {
      border-bottom: 1px solid ${colors.gray003};
    }
  `}

  ${({ animationTime }: { animationTime: number }) => css`
    animation: ${showDropdown} ${animationTime}ms;

    &.close {
      animation: ${hideDropdown} ${animationTime}ms;
    }
  `};
`;

const User = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;

  width: 100%;
  height: 10rem;
`;

const Option = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 3rem;
`;

export { Container, Profile, ToggleButton, Dropdown, User, Option };
