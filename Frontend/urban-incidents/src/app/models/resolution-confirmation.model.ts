export interface ResolutionConfirmation {
  id?: number;
  isResolved: boolean;
  feedback?: string;
  confirmationDate?: string;
  citizenId: number;
  incidentId: number;
}
