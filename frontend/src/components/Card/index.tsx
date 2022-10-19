import { memo } from 'react';

import { Link } from 'react-router-dom';

import { EmptyHeartSVG, FilledHeartSVG } from 'assets/svg';
import { GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import theme from 'styles/theme';
import { GroupSummary } from 'types/data';
import { convertDeadlineToRemainTime } from 'utils/date';

import * as S from './index.styled';

interface CardProps {
  group: GroupSummary;
}

function Card({
  group: {
    id,
    name,
    categoryId,
    host,
    deadline,
    finished,
    numOfParticipant,
    capacity,
    like,
    imageUrl,
  },
}: CardProps) {
  const svgSize = document.body.clientWidth > theme.breakpoints.md ? 30 : 25;

  return (
    <Link to={`${BROWSER_PATH.DETAIL}/${id}`}>
      <S.Container finished={finished}>
        <S.Image imgSrc={imageUrl} />
        <S.Description>
          <S.Left>
            <div>
              <S.Title>{name}</S.Title>
              <S.HostName>
                {host.name || GUIDE_MESSAGE.MEMBER.WITHDRAWAL_MEMBER}
              </S.HostName>
            </div>
            <S.Deadline>
              {finished ? '마감 완료' : convertDeadlineToRemainTime(deadline)}
            </S.Deadline>
          </S.Left>
          <S.Right>
            <div>
              {like ? (
                <FilledHeartSVG width={svgSize} height={svgSize} />
              ) : (
                <EmptyHeartSVG width={svgSize} height={svgSize} />
              )}
            </div>
            <S.Capacity>
              <span>{numOfParticipant}</span>명 / <span>{capacity}</span>명
            </S.Capacity>
          </S.Right>
        </S.Description>
      </S.Container>
    </Link>
  );
}

export default memo(Card);
