export interface CategoryType {
  id: string;
  name: string;
}

export interface PageType {
  number: number;
  content: string;
}

export interface DetailData {
  name: string;
  host: {
    id: number;
    name: string;
  };
  categoryId: number;
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

export type Group = Pick<
  DetailData,
  'name' | 'host' | 'categoryId' | 'deadline'
> & { id: number };
