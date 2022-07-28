import { useState } from 'react';

import { GROUP_RULE } from 'constants/rule';
import { CategoryType, CreateGroupData, Group } from 'types/data';

// TODO: input이 사용되는 곳에는 useInput으로 바꾸기
const useCreateState = () => {
  const [name, setName] = useState('');
  const [selectedCategory, setSelectedCategory] = useState<CategoryType>({
    id: -1,
    name: '',
  });
  const [capacity, setCapacity] = useState<CreateGroupData['capacity']>(0);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [deadline, setDeadline] = useState('');
  const [location, setLocation] = useState('');
  const [description, setDescription] = useState('');

  const changeName = (newName: Group['name']) => {
    setName(newName);
  };

  const changeSelectedCategory = (newSelectedCategory: CategoryType) => {
    setSelectedCategory(newSelectedCategory);
  };

  const changeCapacity = (newCapacity: number) => {
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

  const changeStartDate = (newStartDate: string) => {
    setStartDate(newStartDate);
  };

  const changeEndDate = (newEndDate: string) => {
    setEndDate(newEndDate);
  };

  const changeDeadline = (newDeadline: string) => {
    setDeadline(newDeadline);
  };

  const changeLocation = (newLocation: string) => {
    setLocation(newLocation);
  };

  const changeDescription = (newDescription: string) => {
    setDescription(newDescription);
  };

  return {
    useNameState: () => ({
      name,
      setName: changeName,
    }),
    useSelectedCategoryState: () => ({
      selectedCategory,
      setSelectedCategory: changeSelectedCategory,
    }),
    useCapacityState: () => ({
      capacity,
      setCapacity: changeCapacity,
    }),
    useDateState: () => ({
      startDate,
      setStartDate: changeStartDate,
      endDate,
      setEndDate: changeEndDate,
    }),
    useDeadlineState: () => ({
      deadline,
      setDeadline: changeDeadline,
    }),
    useLocationState: () => ({
      location,
      setLocation: changeLocation,
    }),
    useDescriptionState: () => ({
      description,
      setDescription: changeDescription,
    }),
    getGroupState: () => ({
      name,
      selectedCategory,
      capacity,
      startDate,
      endDate,
      deadline,
      location,
      description,
    }),
  };
};

export default useCreateState;
