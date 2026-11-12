export type TechnicianSpecialty =
  | 'ELECTRICITY'
  | 'PLUMBING'
  | 'ROADWORK'
  | 'SANITATION'
  | 'GENERAL';

export type AvailabilityStatus = 'AVAILABLE' | 'BUSY' | 'OFF_DUTY';

export interface Technician {
  id?: number;
  fullName: string;
  email: string;
  phone: string;
  specialization: TechnicianSpecialty;
  availabilityStatus: AvailabilityStatus;
}
