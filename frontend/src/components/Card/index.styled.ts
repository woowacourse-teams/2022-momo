import styled from '@emotion/styled';

import { oneLineEllipsis } from 'styles/common';

const Container = styled.div<{ finished: boolean }>`
  display: flex;
  justify-content: space-between;
  gap: 0.5rem;

  width: 100%;
  height: 7rem;

  border-top: 1px solid ${({ theme: { colors } }) => colors.gray002};

  transition: transform 0.2s;

  filter: ${({ finished }) =>
    finished ? `contrast(50%) grayscale(100%)` : ''};

  cursor: pointer;

  &:hover {
    transform: scale3d(1.02, 1.02, 1.02);
  }
`;

const Image = styled.div<{ imgSrc: string }>`
  width: 25%;
  height: 70%;

  border-radius: 20px;

  background: url(${({ imgSrc }) => imgSrc});
  background-size: cover;
  background-position: center;

  margin: auto;
  margin-left: 0.5rem;
`;

const Description = styled.div`
  display: flex;
  justify-content: space-between;

  width: 70%;
  line-height: 1.4rem;

  padding: 1rem 3%;

  font-size: 1rem;
`;

const Left = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 60%;
  height: 100%;
`;

const Right = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;

  width: 40%;
  height: 100%;
`;

const Title = styled.div`
  font-weight: 700;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 1rem;
    }

    @media only screen and (min-width: ${breakpoints.md}px) {
      font-size: 1.2rem;
    }
  `}

  ${oneLineEllipsis}
`;

const HostName = styled.div`
  font-weight: 100;

  ${({ theme: { colors, breakpoints } }) => `
    color: ${colors.gray001};

    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.9rem;
    }
  `}

  ${oneLineEllipsis}
`;

const Deadline = styled.div`
  min-width: 40%;
  max-width: 90%;

  font-weight: 700;

  ${({ theme: { colors, breakpoints } }) => `
    color: ${colors.red003};

    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.9rem;
    }
  `}
`;

const Capacity = styled.div`
  ${({ theme: { colors, breakpoints } }) => `
    color: ${colors.gray001};

    span {
      color: ${colors.blue002};
    }

    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.9rem;
    }
  `}
`;

export {
  Container,
  Image,
  Description,
  Left,
  Right,
  Title,
  HostName,
  Deadline,
  Capacity,
};
