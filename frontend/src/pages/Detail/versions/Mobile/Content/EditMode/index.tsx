import { Address } from 'react-daum-postcode';
import { useQueryClient } from 'react-query';
import { useResetRecoilState } from 'recoil';

import { requestEditGroup } from 'apis/request/group';
import CalendarEditor from 'components/CalendarEditor';
import Postcode from 'components/Postcode';
import { QUERY_KEY } from 'constants/key';
import { GUIDE_MESSAGE } from 'constants/message';
import { GROUP_RULE, SNACKBAR_ANIMATION_TIME } from 'constants/rule';
import useCategory from 'hooks/useCategory';
import useCreateState from 'hooks/useCreateState';
import useHandleError from 'hooks/useHandleError';
import useModal from 'hooks/useModal';
import useMount from 'hooks/useMount';
import useSnackbar from 'hooks/useSnackbar';
import { validateGroupData } from 'pages/Create/validate';
import { groupDetailState } from 'store/states';
import { GroupDetailData, GroupSummary } from 'types/data';
import { getNewDateString } from 'utils/date';

import ControlButton from './ControlButton';
import * as S from './index.styled';

interface EditModeProps {
  id: GroupSummary['id'];
  data: GroupDetailData;
  finishEditMode: () => void;
}

function EditMode({ id, data, finishEditMode }: EditModeProps) {
  const categories = useCategory();
  const resetGroupData = useResetRecoilState(groupDetailState);

  const { showPostcodeModal } = useModal();
  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const {
    useNameState,
    useSelectedCategoryState,
    useDateState,
    useScheduleState,
    useDeadlineState,
    useLocationState,
    useDescriptionState,
    useCapacityState,
    getGroupState,
  } = useCreateState();

  const { name, setName, dangerouslySetName } = useNameState();
  const { selectedCategory, setSelectedCategory } = useSelectedCategoryState();
  const {
    startDate,
    setStartDate,
    endDate,
    setEndDate,
    dangerouslySetStartDate,
    dangerouslySetEndDate,
  } = useDateState();
  const { dangerouslySetSchedules } = useScheduleState();
  const { deadline, setDeadline, dangerouslySetDeadline } = useDeadlineState();
  const { location, setLocationAddress, setLocationDetail } =
    useLocationState();
  const { description, setDescription, dangerouslySetDescription } =
    useDescriptionState();
  const { capacity, setCapacity, dangerouslySetCapacity } = useCapacityState();

  const queryClient = useQueryClient();

  useMount(() => {
    dangerouslySetName(data.name);
    setSelectedCategory({
      id: data.categoryId,
      name: categories[data.categoryId - 1 || 0].name,
    });
    dangerouslySetCapacity(data.capacity);
    dangerouslySetStartDate(data.duration.start);
    dangerouslySetEndDate(data.duration.end);
    dangerouslySetSchedules(data.schedules);
    dangerouslySetDeadline(data.deadline);
    setLocationAddress(
      data.location.address,
      data.location.buildingName,
      data.location.detail,
    );
    dangerouslySetDescription(data.description);
  });

  const editGroup = () => {
    const groupData = getGroupState();

    try {
      validateGroupData(groupData);
    } catch (error) {
      if (!(error instanceof Error)) {
        return;
      }

      setMessage(error.message, true);
      return;
    }

    requestEditGroup(groupData, id)
      .then(() => {
        resetGroupData();

        queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);

        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_EDIT_REQUEST);

        setTimeout(() => {
          finishEditMode();
          window.location.reload();
        }, SNACKBAR_ANIMATION_TIME);
      })
      .catch(error => {
        handleError(error);
      });
  };

  const selectCategory = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const categoryId = Number(e.target.value);

    setSelectedCategory({
      id: categoryId,
      name: categories[categoryId - 1 || 0].name,
    });
  };

  const changeDuration =
    (type: 'start' | 'end') => (e: React.ChangeEvent<HTMLInputElement>) => {
      if (!window.confirm(GUIDE_MESSAGE.GROUP.CONFIRM_CHANGE_DURATION)) return;

      dangerouslySetSchedules([]);

      if (type === 'start') {
        setStartDate(e);
        return;
      }

      setEndDate(e);
    };

  const setLocation = (data: Address) => {
    setLocationAddress(data.address, data.buildingName, '');
  };

  const isNotFulfilledAddress = location.address === '';

  return (
    <S.Container>
      <S.StickyContainer>
        <S.Header>
          <S.TitleContainer>
            <S.Category value={selectedCategory.id} onChange={selectCategory}>
              {categories.map(category => (
                <option value={category.id} key={category.id}>
                  {category.name}
                </option>
              ))}
            </S.Category>
            <S.Title
              type="text"
              placeholder="정체를 밝혀라 ㅇㅁㅇ!"
              value={name}
              onChange={setName}
            />
            <S.Deadline
              type="datetime-local"
              value={deadline}
              onChange={setDeadline}
              min={getNewDateString('min')}
            />
          </S.TitleContainer>
        </S.Header>
      </S.StickyContainer>
      <S.ContentContainer>
        <S.Label>
          기간
          <S.Duration>
            <S.Input
              type="date"
              value={startDate}
              onChange={changeDuration('start')}
              min={getNewDateString('day')}
            />
            ~
            <S.Input
              type="date"
              value={endDate}
              onChange={changeDuration('end')}
              min={startDate || getNewDateString('day')}
            />
          </S.Duration>
        </S.Label>
        <S.Label>
          일정
          <CalendarEditor
            useScheduleState={useScheduleState}
            duration={{ start: startDate, end: endDate }}
          />
        </S.Label>
        <S.Label>
          장소
          <S.Input
            type="text"
            value={
              location.buildingName ? location.buildingName : location.address
            }
            onClick={showPostcodeModal}
            placeholder="한국루터회관"
            maxLength={GROUP_RULE.LOCATION.MAX_LENGTH}
            readOnly
          />
          <S.Input
            type="text"
            value={location.detail}
            onChange={setLocationDetail}
            title={isNotFulfilledAddress ? '주소를 먼저 입력해주세요.' : ''}
            placeholder="14층"
            maxLength={GROUP_RULE.LOCATION.MAX_LENGTH}
            disabled={isNotFulfilledAddress}
          />
        </S.Label>
        <S.Label>
          설명
          <S.Description
            value={description}
            onChange={setDescription}
            maxLength={GROUP_RULE.DESCRIPTION.MAX_LENGTH}
          />
        </S.Label>
        <S.Label>
          최대 인원
          <S.Input
            type="number"
            min="1"
            max="99"
            placeholder="99"
            value={capacity}
            onChange={setCapacity}
          />
        </S.Label>
      </S.ContentContainer>
      <ControlButton editGroup={editGroup} finishEditMode={finishEditMode} />
      <Postcode completeFunc={setLocation} />
    </S.Container>
  );
}

export default EditMode;
