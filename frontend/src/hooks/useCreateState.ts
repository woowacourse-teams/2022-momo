import { useState } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CreateGroupData } from 'types/data';
import { ArrElement } from 'types/utils';
import { isEqualObject } from 'utils/compare';

import useInput from './useInput';

export interface CreateStateReturnValues {
  useNameState: () => {
    name: CreateGroupData['name'];
    setName: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetName: React.Dispatch<
      React.SetStateAction<CreateGroupData['name']>
    >;
  };
  useSelectedCategoryState: () => {
    selectedCategory: CreateGroupData['selectedCategory'];
    setSelectedCategory: (
      newSelectedCategory: CreateGroupData['selectedCategory'],
    ) => void;
  };
  useCapacityState: () => {
    capacity: CreateGroupData['capacity'];
    setCapacity: (e: React.ChangeEvent<HTMLInputElement>) => void;
    dangerouslySetCapacity: (newCapacity: CreateGroupData['capacity']) => void;
  };
  useDateState: () => {
    startDate: CreateGroupData['startDate'];
    setStartDate: (e: React.ChangeEvent<HTMLInputElement>) => void;
    endDate: CreateGroupData['endDate'];
    setEndDate: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetStartDate: React.Dispatch<
      React.SetStateAction<CreateGroupData['startDate']>
    >;
    dangerouslySetEndDate: React.Dispatch<
      React.SetStateAction<CreateGroupData['endDate']>
    >;
  };
  useScheduleState: () => {
    schedules: CreateGroupData['schedules'];
    setSchedules: (
      newSchedule: ArrElement<CreateGroupData['schedules']>,
    ) => void;
    dangerouslySetSchedules: (schedules: CreateGroupData['schedules']) => void;
    deleteSchedule: (
      targetSchedule: ArrElement<CreateGroupData['schedules']>,
    ) => void;
  };
  useDeadlineState: () => {
    deadline: CreateGroupData['deadline'];
    setDeadline: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetDeadline: React.Dispatch<
      React.SetStateAction<CreateGroupData['deadline']>
    >;
  };
  useLocationState: () => {
    location: CreateGroupData['location'];
    setLocation: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetLocation: React.Dispatch<
      React.SetStateAction<CreateGroupData['location']>
    >;
  };
  useDescriptionState: () => {
    description: CreateGroupData['description'];
    setDescription: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetDescription: React.Dispatch<
      React.SetStateAction<CreateGroupData['description']>
    >;
  };
  getGroupState: () => CreateGroupData;
}

const useCreateState = (): CreateStateReturnValues => {
  const {
    value: name,
    setValue: setName,
    dangerouslySetValue: dangerouslySetName,
  } = useInput('');
  const [selectedCategory, setSelectedCategory] = useState<
    CreateGroupData['selectedCategory']
  >({
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

  const changeSelectedCategory = (
    newSelectedCategory: CreateGroupData['selectedCategory'],
  ) => {
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

  const changeSchedules = (
    newSchedule: ArrElement<CreateGroupData['schedules']>,
  ) => {
    setSchedules(prevState => {
      if (!prevState.length) return [newSchedule];

      return [...prevState, newSchedule];
    });
  };

  const dangerouslySetSchedules = (schedules: CreateGroupData['schedules']) => {
    setSchedules([]);

    schedules.forEach(schedule => changeSchedules(schedule));
  };

  const deleteSchedule = (
    targetSchedule: ArrElement<CreateGroupData['schedules']>,
  ) => {
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
      setCapacity: changeCapacity,
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
      dangerouslySetSchedules,
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
