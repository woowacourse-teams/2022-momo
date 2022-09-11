import { useState } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CategoryType, CreateGroupData, ScheduleType } from 'types/data';
import { isEqualObject } from 'utils/compare';

import useInput from './useInput';

const useCreateState = () => {
  const {
    value: name,
    setValue: setName,
    dangerouslySetValue: dangerouslySetName,
  } = useInput('');
  const [selectedCategory, setSelectedCategory] = useState<CategoryType>({
    id: -1,
    name: '',
  });
  const [capacity, setCapacity] = useState<CreateGroupData['capacity']>(0);
  const {
    value: startDate,
    setValue: setStartDate,
    dangerouslySetValue: dangerouslySetStartDate,
  } = useInput('');
  const {
    value: endDate,
    setValue: setEndDate,
    dangerouslySetValue: dangerouslySetEndDate,
  } = useInput('');
  const [schedules, setSchedules] = useState<CreateGroupData['schedules']>([]);
  const {
    value: deadline,
    setValue: setDeadline,
    dangerouslySetValue: dangerouslySetDeadline,
  } = useInput('');
  const {
    value: location,
    setValue: setLocation,
    dangerouslySetValue: dangerouslySetLocation,
  } = useInput('');
  const {
    value: description,
    setValue: setDescription,
    dangerouslySetValue: dangerouslySetDescription,
  } = useInput('');

  const changeSelectedCategory = (newSelectedCategory: CategoryType) => {
    setSelectedCategory(newSelectedCategory);
  };

  const changeCapacity = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newCapacity = Number(e.target.value);
    const { MIN, MAX } = GROUP_RULE.CAPACITY;

    if (newCapacity < MIN) {
      setCapacity(MIN);
      return;
    }
    if (newCapacity > MAX) {
      setCapacity(MAX);
      return;
    }

    setCapacity(newCapacity);
  };

  const changeStartDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newDate = e.target.value;

    setStartDate(e);

    if (newDate > endDate) {
      setEndDate(e);
    }
  };

  const changeSchedules = (newSchedule: ScheduleType) => {
    setSchedules(prevState => {
      if (!prevState.length) return [newSchedule];

      return [...prevState, newSchedule];
    });
  };

  const deleteSchedule = (targetSchedule: ScheduleType) => {
    setSchedules(
      schedules.filter(schedule => isEqualObject(schedule, targetSchedule)),
    );
  };

  return {
    useNameState: () => ({
      name,
      setName,
      dangerouslySetName,
    }),
    useSelectedCategoryState: () => ({
      selectedCategory,
      setSelectedCategory: changeSelectedCategory,
    }),
    useCapacityState: () => ({
      capacity,
      changeCapacity,
      dangerouslySetCapacity: setCapacity,
    }),
    useDateState: () => ({
      startDate,
      setStartDate: changeStartDate,
      endDate,
      setEndDate,
      dangerouslySetStartDate,
      dangerouslySetEndDate,
    }),
    useScheduleState: () => ({
      schedules,
      setSchedules: changeSchedules,
      deleteSchedule,
    }),
    useDeadlineState: () => ({
      deadline,
      setDeadline,
      dangerouslySetDeadline,
    }),
    useLocationState: () => ({
      location,
      setLocation,
      dangerouslySetLocation,
    }),
    useDescriptionState: () => ({
      description,
      setDescription,
      dangerouslySetDescription,
    }),
    getGroupState: () => ({
      name,
      selectedCategory,
      capacity,
      startDate,
      endDate,
      schedules,
      deadline,
      location,
      description,
    }),
  };
};

export default useCreateState;
