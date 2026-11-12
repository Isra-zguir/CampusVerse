export type IncidentCategory =
  | 'POTHOLE'
  | 'STREETLIGHT'
  | 'WATER_LEAK'
  | 'ROAD_DAMAGE'
  | 'WASTE'
  | 'OTHER';

export type IncidentStatus =
  | 'REPORTED'
  | 'PENDING_INFO'
  | 'VALIDATED'
  | 'REJECTED'
  | 'IN_PROGRESS'
  | 'RESOLVED'
  | 'CLOSED';

export type Priority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type Severity = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';

export interface Incident {
  id?: number;
  title: string;
  description: string;
  category: IncidentCategory;
  photoUris: string[];
  latitude: number;
  longitude: number;
  submissionDate?: string;
  status: IncidentStatus;
  priority: Priority;
  severity: Severity;
  citizenId: number;
}
