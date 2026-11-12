export interface Intervention {
  id?: number;
  startDate?: string;
  endDate?: string;
  workDescription: string;
  result?: string;
  workOrderId: number;
  technicianId: number;
}
