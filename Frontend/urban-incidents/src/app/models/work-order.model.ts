import { Priority } from './incident.model';

export type WorkOrderStatus =
  | 'OPEN'
  | 'ASSIGNED'
  | 'IN_PROGRESS'
  | 'COMPLETED'
  | 'CANCELLED';

export interface WorkOrder {
  id?: number;
  title: string;
  description: string;
  priority: Priority;
  status: WorkOrderStatus;
  plannedDate?: string;
  startTime?: string;
  estimatedDuration?: number;
  incidentId: number;
  technicianId?: number;
}
