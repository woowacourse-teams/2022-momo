import CalendarEditor from 'components/CalendarEditor';
import { GROUP_RULE } from 'constants/rule';
import useClosingState from 'hooks/useClosingState';
import { CreateStateReturnValues } from 'hooks/useCreateState';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { categoryState, groupDetailState, modalState } from 'store/states';
import { CategoryType } from 'types/data';
import { getNewDateString } from 'utils/date';
import * as S from './index.styled';

const dropdownAnimationTime = 300;

function Inputs({
  useNameState,
  useSelectedCategoryState,
  useCapacityState,
  useDateState,
  useScheduleState,
  useDeadlineState,
  useLocationState,
  useDescriptionState,
}: Omit<CreateStateReturnValues, 'getGroupState'>) {
  const { name, setName, dangerouslySetName } = useNameState();
  const { selectedCategory, setSelectedCategory } = useSelectedCategoryState();
  const { capacity, setCapacity } = useCapacityState();
  const {
    startDate,
    setStartDate,
    endDate,
    setEndDate,
    dangerouslySetStartDate,
    dangerouslySetEndDate,
  } = useDateState();
  const { setSchedules } = useScheduleState();
  const { deadline, setDeadline, dangerouslySetDeadline } = useDeadlineState();
  const { location, setLocation, dangerouslySetLocation } = useLocationState();
  const { description, setDescription, dangerouslySetDescription } =
    useDescriptionState();

  const categories = useRecoilValue(categoryState);
  const groupData = useRecoilValue(groupDetailState);

  const modalFlag = useRecoilValue(modalState);
  const [isShownDropdown, setIsShownDropdown] = useState(false);
  const { isClosing, close } = useClosingState(dropdownAnimationTime, () => {
    setIsShownDropdown(false);
  });

  useEffect(() => {
    dangerouslySetName(groupData.name);
    setSelectedCategory({
      id: groupData.categoryId,
      name: categories[groupData.categoryId - 1 || 0].name,
    });
    setCapacity(groupData.capacity);
    dangerouslySetStartDate(groupData.duration.start);
    dangerouslySetEndDate(groupData.duration.end);
    groupData.schedules.forEach(schedule => setSchedules(schedule));
    dangerouslySetDeadline(groupData.deadline);
    dangerouslySetLocation(groupData.location);
    dangerouslySetDescription(groupData.description);
  }, [modalFlag]);

  const toggleDropdownState = () => {
    if (isShownDropdown) {
      close();
      return;
    }

    setIsShownDropdown(true);
  };

  const changeCategory = (item: CategoryType) => () => {
    setSelectedCategory(item);
    close();
  };

  const changeCapacity = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCapacity(Number(e.target.value));
  };

  return (
    <S.InputContainer>
      <S.Label>
        이름
        <S.Input
          type="text"
          placeholder="정체를 밝혀라 ㅇㅁㅇ!"
          value={name}
          onChange={setName}
        />
      </S.Label>
      <S.Label>
        카테고리
        <S.CategoryButton type="button" onClick={toggleDropdownState}>
          {selectedCategory.name}
        </S.CategoryButton>
        {isShownDropdown && (
          <S.CategoryBox
            className={isClosing ? 'close' : ''}
            animationTime={dropdownAnimationTime}
          >
            {categories &&
              categories.map(item => (
                <S.CategoryList onClick={changeCategory(item)} key={item.id}>
                  {item.name}
                </S.CategoryList>
              ))}
          </S.CategoryBox>
        )}
      </S.Label>
      <S.Label>
        최대 인원 수
        <S.Input
          type="number"
          min="1"
          max="99"
          placeholder="99"
          value={capacity}
          onChange={changeCapacity}
        />
      </S.Label>
      <S.Label>
        기간
        <S.SingleLineContainer>
          <S.Input
            type="date"
            value={startDate}
            onChange={setStartDate}
            min={getNewDateString('day')}
          />
          ~
          <S.Input
            type="date"
            value={endDate}
            onChange={setEndDate}
            min={startDate || getNewDateString('day')}
          />
        </S.SingleLineContainer>
      </S.Label>
      <S.Label>
        상세 일정
        <CalendarEditor
          useScheduleState={useScheduleState}
          duration={groupData.duration}
        />
      </S.Label>
      <S.Label>
        모집 마감일
        <S.Input
          type="datetime-local"
          value={deadline}
          onChange={setDeadline}
          min={getNewDateString('min')}
        />
      </S.Label>
      <S.Label>
        장소
        <S.Input type="text" value={location} onChange={setLocation} />
      </S.Label>
      <S.Label>
        설명
        <S.TextArea
          value={description}
          onChange={setDescription}
          maxLength={GROUP_RULE.DESCRIPTION.MAX_LENGTH}
        />
      </S.Label>
    </S.InputContainer>
  );
}

export default Inputs;
