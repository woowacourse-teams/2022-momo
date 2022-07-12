export interface DetailData {
  name: string;
  host: {
    id: number;
    name: string;
  };
  categoryId: number;
  regular: boolean;
  duration: {
    start: Date;
    end: Date;
  };
  schedules: [
    {
      day: string;
      time: {
        start: string;
        end: string;
      };
    },
  ];
  deadline: Date;
  location: string;
  description: string;
}

export type Group = Pick<
  DetailData,
  'name' | 'host' | 'categoryId' | 'regular' | 'deadline'
> & { id: number };
