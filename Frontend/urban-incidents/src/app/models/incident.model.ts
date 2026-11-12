export type IncidentCategory =
  | 'POTHOLE' | 'STREETLIGHT' | 'WATER_LEAK' | 'GRAFFITI' | 'WASTE' | 'ROAD_DAMAGE' | 'OTHER';

export type IncidentStatus =
  | 'REPORTED' | 'PENDING_INFO' | 'VALIDATED' | 'REJECTED' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED';

export type Priority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';

export type InterventionStatus = 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';

export const INCIDENT_CATEGORIES: IncidentCategory[] =
  ['POTHOLE', 'STREETLIGHT', 'WATER_LEAK', 'GRAFFITI', 'WASTE', 'ROAD_DAMAGE', 'OTHER'];

export const INCIDENT_STATUSES: IncidentStatus[] =
  ['REPORTED', 'PENDING_INFO', 'VALIDATED', 'REJECTED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED'];

export const PRIORITIES: Priority[] = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];

export interface Incident {
  id: number;
  title: string;
  description: string | null;
  photoUrl: string | null;
  location: string;
  neighborhood: string | null;
  category: IncidentCategory;
  priority: Priority;
  status: IncidentStatus;
  reporterId: number;
  extraInfoRequest: string | null;
  rejectionReason: string | null;
  interventionCount: number;
  createdAt: string;
  updatedAt: string;
  resolvedAt: string | null;
}

export interface IncidentRequest {
  title: string;
  description?: string | null;
  photoUrl?: string | null;
  location: string;
  neighborhood?: string | null;
  category: IncidentCategory;
  priority?: Priority | null;
  reporterId: number;
}

export interface Intervention {
  id: number;
  incidentId: number;
  incidentTitle: string;
  technicianId: number;
  scheduledDate: string | null;
  status: InterventionStatus;
  notes: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface Statistics {
  totalIncidents: number;
  openIncidents: number;
  resolvedIncidents: number;
  completedInterventions: number;
  averageResolutionHours: number | null;
  byCategory: Record<string, number>;
  byPriority: Record<string, number>;
  byStatus: Record<string, number>;
}

export interface IncidentFilters {
  category?: IncidentCategory | '';
  status?: IncidentStatus | '';
  priority?: Priority | '';
}

export interface ApiError {
  status: number;
  error: string;
  message: string;
  fieldErrors?: Record<string, string> | null;
}
