import { useEffect, useState } from 'react';

import { GROUP_RULE } from 'constants/rule';
import {
  validateDeadlineDate,
  validateDurationDate,
  validateName,
} from 'pages/Create/validate';
import { CreateGroupData, RequiredGroupDataBooleanType } from 'types/data';
import { ArrElement } from 'types/utils';
import { resetDateToStartOfDay, resetDateToEndOfDay } from 'utils/date';
import { isEqualObject } from 'utils/object';

import useInput from './useInput';

export interface CreateStateReturnValues {
  useNameState: () => {
    name: CreateGroupData['name'];
    setName: (e: React.ChangeEvent<HTMLInputElement>) => void;
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
    blurCapacity: (e: React.FocusEvent<HTMLInputElement>) => void;
    dangerouslySetCapacity: (newCapacity: CreateGroupData['capacity']) => void;
  };
  useDateState: () => {
    startDate: CreateGroupData['startDate'];
    setStartDate: (e: React.ChangeEvent<HTMLInputElement>) => void;
    endDate: CreateGroupData['endDate'];
    setEndDate: (e: React.ChangeEvent<HTMLInputElement>) => void;
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
    setDeadline: (e: React.ChangeEvent<HTMLInputElement>) => void;
    dangerouslySetDeadline: React.Dispatch<
      React.SetStateAction<CreateGroupData['deadline']>
    >;
  };
  useLocationState: () => {
    location: CreateGroupData['location'];
    setLocationAddress: (
      address: CreateGroupData['location']['address'],
      buildingName: CreateGroupData['location']['buildingName'],
      detail?: CreateGroupData['location']['detail'],
    ) => void;
    setLocationDetail: (e: React.ChangeEvent<HTMLInputElement>) => void;
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
  isEmptyInput: RequiredGroupDataBooleanType;
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
  const [location, setLocation] = useState({
    address: '',
    buildingName: '',
    detail: '',
  });
  const {
    value: description,
    setValue: setDescription,
    dangerouslySetValue: dangerouslySetDescription,
  } = useInput('');
  const [isEmptyInput, setIsEmptyInput] =
    useState<RequiredGroupDataBooleanType>({
      name: false,
      startDate: false,
      endDate: false,
      deadline: false,
    });

  const changeSelectedCategory = (
    newSelectedCategory: CreateGroupData['selectedCategory'],
  ) => {
    setSelectedCategory(newSelectedCategory);
  };

  const changeCapacity = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newCapacityString = e.target.value;
    if (newCapacityString === '') {
      setCapacity(0);
      return;
    }

    const newCapacity = Number(newCapacityString);
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

  const blurCapacity = (e: React.FocusEvent<HTMLInputElement>) => {
    const replacedCapacity = e.target.value.replace(/[^0-9]/g, '');

    setCapacity(Number(replacedCapacity));
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

  const setLocationAddress = (
    address: CreateGroupData['location']['address'],
    buildingName: CreateGroupData['location']['buildingName'],
    detail?: CreateGroupData['location']['detail'],
  ) => {
    if (typeof detail === 'string') {
      setLocation({ address, buildingName, detail });
      return;
    }

    setLocation({ ...location, address, buildingName });
  };

  const changeLocationDetail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLocation({ ...location, detail: e.target.value });
  };

  useEffect(() => {
    const startDateInMidnight = resetDateToStartOfDay(new Date(startDate));
    const endDateInMidnight = resetDateToEndOfDay(new Date(endDate));
    const todayInMidnight = new Date();

    const validateDate = validateDurationDate(
      startDateInMidnight,
      endDateInMidnight,
      todayInMidnight,
    )();

    const newInputState = {
      name: validateName(name)(),
      startDate: validateDate,
      endDate: validateDate,
      deadline: validateDeadlineDate(
        startDateInMidnight,
        deadline,
        schedules,
      )(),
    };

    setIsEmptyInput(newInputState);
  }, [name, startDate, endDate, deadline, schedules]);

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
      blurCapacity,
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
      setLocationAddress,
      setLocationDetail: changeLocationDetail,
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
    isEmptyInput,
  };
};

export default useCreateState;
