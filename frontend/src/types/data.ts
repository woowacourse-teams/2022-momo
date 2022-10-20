import { UserProfile } from './user';

export interface CategoryType {
  id: GroupDetailData['categoryId'];
  name: string;
  imageUrl: string;
}

export interface ScheduleType {
  date: string;
  startTime: string;
  endTime: string;
}

export interface PageType {
  number: number;
  content: string;
  required: boolean;
}

export interface DurationDate {
  startDate: GroupDetailData['duration']['start'];
  endDate: GroupDetailData['duration']['end'];
}

export interface GroupDetailData {
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
  imageUrl: string;
}

export interface CreateGroupData
  extends DurationDate,
    Pick<
      GroupDetailData,
      | 'name'
      | 'capacity'
      | 'schedules'
      | 'deadline'
      | 'location'
      | 'description'
    > {
  selectedCategory: Omit<CategoryType, 'imageUrl'>;
}

export interface GroupList {
  pageNumber: number;
  groups: GroupSummary[];
  hasNextPage: boolean;
}

export interface GroupSummary
  extends Pick<
    GroupDetailData,
    | 'name'
    | 'host'
    | 'categoryId'
    | 'deadline'
    | 'finished'
    | 'capacity'
    | 'like'
    | 'imageUrl'
  > {
  id: number;
  numOfParticipant: number;
}

export interface RequiredGroupDataBooleanType {
  name: boolean;
  startDate: boolean;
  endDate: boolean;
  deadline: boolean;
}

export type GroupParticipants = Omit<UserProfile, 'userId'>[];

export type SelectableGroup = 'participated' | 'hosted' | 'liked';

export interface ServerErrorType {
  response: {
    data: {
      message: string;
    };
  };
}
