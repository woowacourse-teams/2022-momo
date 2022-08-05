import { UserProfile } from './user';

export interface CategoryType {
  id: GroupDetailData['categoryId'];
  name: string;
}

export interface ScheduleType {
  date: string;
  startTime: string;
  endTime: string;
}

export interface PageType {
  number: number;
  content: string;
}

export interface CreateGroupData {
  name: GroupDetailData['name'];
  selectedCategory: CategoryType;
  capacity: GroupDetailData['capacity'];
  startDate: GroupDetailData['duration']['start'];
  endDate: GroupDetailData['duration']['end'];
  schedules: GroupDetailData['schedules'];
  deadline: GroupDetailData['deadline'];
  location: GroupDetailData['location'];
  description: GroupDetailData['description'];
}

export interface GroupDetailData {
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
  schedules: ScheduleType[];
  finished: boolean;
  deadline: string;
  location: string;
  description: string;
}

export interface DurationDate {
  startDate: GroupDetailData['duration']['start'];
  endDate: GroupDetailData['duration']['end'];
}

export interface GroupList {
  pageNumber: number;
  groups: GroupSummary[];
  hasNextPage: boolean;
}

export type GroupSummary = Pick<
  GroupDetailData,
  'id' | 'name' | 'host' | 'categoryId' | 'deadline' | 'finished' | 'capacity'
> & { numOfParticipant: number };

export type GroupParticipants = Omit<UserProfile, 'userId'>[];
