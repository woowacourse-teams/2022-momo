import { useState } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CategoryType, CreateGroupData, ScheduleType } from 'types/data';
import { isEqualObject } from 'utils/compare';

import useInput from './useInput';

export interface CreateStateReturnValues {
  useNameState: () => {
    name: string;
    setName: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetName: React.Dispatch<React.SetStateAction<string>>;
  };
  useSelectedCategoryState: () => {
    selectedCategory: CategoryType;
    setSelectedCategory: (newSelectedCategory: CategoryType) => void;
  };
  useCapacityState: () => {
    capacity: number;
    setCapacity: (e: React.ChangeEvent<HTMLInputElement>) => void;
    dangerouslySetCapacity: (newCapacity: number) => void;
  };
  useDateState: () => {
    startDate: string;
    setStartDate: (e: React.ChangeEvent<HTMLInputElement>) => void;
    endDate: string;
    setEndDate: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetStartDate: React.Dispatch<React.SetStateAction<string>>;
    dangerouslySetEndDate: React.Dispatch<React.SetStateAction<string>>;
  };
  useScheduleState: () => {
    schedules: ScheduleType[];
    setSchedules: (newSchedule: ScheduleType) => void;
    dangerouslySchedules: (schedules: ScheduleType[]) => void;
    deleteSchedule: (targetSchedule: ScheduleType) => void;
  };
  useDeadlineState: () => {
    deadline: string;
    setDeadline: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetDeadline: React.Dispatch<React.SetStateAction<string>>;
  };
  useLocationState: () => {
    location: string;
    setLocation: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetLocation: React.Dispatch<React.SetStateAction<string>>;
  };
  useDescriptionState: () => {
    description: string;
    setDescription: (
      e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    dangerouslySetDescription: React.Dispatch<React.SetStateAction<string>>;
  };
  getGroupState: () => {
    name: string;
    selectedCategory: CategoryType;
    capacity: number;
    startDate: string;
    endDate: string;
    schedules: ScheduleType[];
    deadline: string;
    location: string;
    description: string;
  };
}

const useCreateState = (): CreateStateReturnValues => {
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

  const dangerouslySchedules = (schedules: ScheduleType[]) => {
    setSchedules([]);

    schedules.forEach(schedule => changeSchedules(schedule));
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
      dangerouslySchedules,
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
