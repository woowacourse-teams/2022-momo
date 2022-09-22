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

export interface DurationDate {
  startDate: GroupDetailData['duration']['start'];
  endDate: GroupDetailData['duration']['end'];
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
  like: boolean;
  location: Record<'address' | 'buildingName' | 'detail', string>;
  description: string;
}

export interface CreateGroupData extends DurationDate {
  name: GroupDetailData['name'];
  selectedCategory: CategoryType;
  capacity: GroupDetailData['capacity'];
  schedules: GroupDetailData['schedules'];
  deadline: GroupDetailData['deadline'];
  location: GroupDetailData['location'];
  description: GroupDetailData['description'];
}

export interface GroupList {
  pageNumber: number;
  groups: GroupSummary[];
  hasNextPage: boolean;
}

export interface GroupSummary {
  id: GroupDetailData['id'];
  name: GroupDetailData['name'];
  host: GroupDetailData['host'];
  categoryId: GroupDetailData['categoryId'];
  deadline: GroupDetailData['deadline'];
  finished: GroupDetailData['finished'];
  capacity: GroupDetailData['capacity'];
  like: GroupDetailData['like'];
  numOfParticipant: number;
}

// export type GroupSummary = Pick<
//   GroupDetailData,
//   | 'id'
//   | 'name'
//   | 'host'
//   | 'categoryId'
//   | 'deadline'
//   | 'finished'
//   | 'capacity'
//   | 'like'
// > & { numOfParticipant: number };

export type GroupParticipants = Omit<UserProfile, 'userId'>[];

export type SelectableGroup = 'participated' | 'hosted' | 'liked';
