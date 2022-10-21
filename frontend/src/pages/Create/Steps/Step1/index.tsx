import { memo } from 'react';

import { GROUP_RULE } from 'constants/rule';
import useCategory from 'hooks/useCategory';
import { CreateStateReturnValues } from 'hooks/useCreateState';

import {
  Container,
  SectionContainer,
  Heading,
  Input,
  LabelContainer,
  Label,
} from '../@shared/styled';
import * as S from './index.styled';

interface Step1Props {
  useNameState: CreateStateReturnValues['useNameState'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
  useSelectedCategoryState: CreateStateReturnValues['useSelectedCategoryState'];
  gotoNextPage: () => void;
}

function Step1({
  useNameState,
  pressEnterToNext,
  useSelectedCategoryState,
  gotoNextPage,
}: Step1Props) {
  const { name, setName } = useNameState();
  const { selectedCategory, setSelectedCategory } = useSelectedCategoryState();
  const categories = useCategory();

  const selectCategory = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const categoryId = Number(e.target.value);

    setSelectedCategory({
      id: categoryId,
      name: categories[categoryId - 1 || 0].name,
    });

    gotoNextPage();
  };

  return (
    <Container>
      <SectionContainer>
        <Heading>
          모임의 <span>이름</span>을 알려주세요.
        </Heading>
        <LabelContainer>
          <Label>
            <p>이름</p>
            <p>
              {name.length}/{GROUP_RULE.NAME.MAX_LENGTH}
            </p>
          </Label>
          <Input
            type="text"
            value={name}
            onChange={setName}
            onKeyPress={pressEnterToNext}
            placeholder="정체를 밝혀라 @_@"
            maxLength={GROUP_RULE.NAME.MAX_LENGTH}
            autoFocus
          />
        </LabelContainer>
      </SectionContainer>
      <SectionContainer>
        <Heading>
          <span>어떤</span> 모임인가요?
          <p>(카테고리 선택)</p>
        </Heading>
        <S.OptionBox
          value={selectedCategory.id || '카테고리를 선택해주세요'}
          onChange={selectCategory}
        >
          {categories &&
            categories.map(category => (
              <option value={category.id} key={category.id}>
                {category.name}
              </option>
            ))}
        </S.OptionBox>
      </SectionContainer>
    </Container>
  );
}

export default memo(Step1);
