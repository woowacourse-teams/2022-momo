import { UserProfile } from './user';

export interface CategoryType {
  id: DetailData['categoryId'];
  name: string;
}

export interface PageType {
  number: number;
  content: string;
}

export interface CreateGroupData {
  name: string;
  selectedCategory: CategoryType;
  capacity: number;
  startDate: string;
  endDate: string;
  deadline: string;
  location: string;
  description: string;
}

// TODO: GroupDetailData
export interface DetailData {
  id: number;
  name: string;
  host: {
    id: number;
    name: string;
  };
  categoryId: number;
  capacity: number;
  duration: {
    start: string;
    end: string;
  };
  schedules: [
    {
      date: string;
      startTime: string;
      endTime: string;
    },
  ];
  deadline: string;
  location: string;
  description: string;
}

export interface DurationDate {
  startDate: DetailData['duration']['start'];
  endDate: DetailData['duration']['end'];
}

// TODO: GroupSummary으로 이름 변경 필요
export type Group = Pick<
  DetailData,
  'id' | 'name' | 'host' | 'categoryId' | 'deadline'
>;

export type GroupParticipants = Omit<UserProfile, 'email'>[];
