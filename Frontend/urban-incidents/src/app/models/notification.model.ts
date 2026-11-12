export type RecipientType = 'CITIZEN' | 'TECHNICIAN' | 'ADMIN';

export type NotificationType =
  | 'STATUS_CHANGE'
  | 'VALIDATION'
  | 'REJECTION'
  | 'INTERVENTION_UPDATE'
  | 'RESOLUTION';

export interface Notification {
  id?: number;
  recipientId: number;
  recipientType: RecipientType;
  message: string;
  notificationType: NotificationType;
  isRead: boolean;
  createdAt?: string;
}
