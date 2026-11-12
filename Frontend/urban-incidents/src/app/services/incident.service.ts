import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import {
  ApiError,
  Incident,
  IncidentFilters,
  IncidentRequest,
  Intervention,
  Statistics
} from '../models/incident.model';

@Injectable({ providedIn: 'root' })
export class IncidentService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = '/api/incidents';

  getAll(filters: IncidentFilters = {}): Observable<Incident[]> {
    let params = new HttpParams();
    if (filters.category) params = params.set('category', filters.category);
    if (filters.status) params = params.set('status', filters.status);
    if (filters.priority) params = params.set('priority', filters.priority);
    return this.http.get<Incident[]>(this.baseUrl, { params });
  }

  getById(id: number): Observable<Incident> {
    return this.http.get<Incident>(`${this.baseUrl}/${id}`);
  }

  create(request: IncidentRequest): Observable<Incident> {
    return this.http.post<Incident>(this.baseUrl, request);
  }

  update(id: number, request: IncidentRequest): Observable<Incident> {
    return this.http.put<Incident>(`${this.baseUrl}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  validate(id: number): Observable<Incident> {
    return this.http.patch<Incident>(`${this.baseUrl}/${id}/validate`, {});
  }

  reject(id: number, reason: string): Observable<Incident> {
    return this.http.patch<Incident>(`${this.baseUrl}/${id}/reject`, { reason });
  }

  requestInfo(id: number, reason: string): Observable<Incident> {
    return this.http.patch<Incident>(`${this.baseUrl}/${id}/request-info`, { reason });
  }

  close(id: number): Observable<Incident> {
    return this.http.patch<Incident>(`${this.baseUrl}/${id}/close`, {});
  }

  classify(id: number): Observable<Incident> {
    return this.http.patch<Incident>(`${this.baseUrl}/${id}/classify`, {});
  }

  prioritize(id: number): Observable<Incident> {
    return this.http.patch<Incident>(`${this.baseUrl}/${id}/prioritize`, {});
  }

  statistics(): Observable<Statistics> {
    return this.http.get<Statistics>(`${this.baseUrl}/statistics`);
  }

  getInterventions(incidentId: number): Observable<Intervention[]> {
    return this.http.get<Intervention[]>(`${this.baseUrl}/${incidentId}/interventions`);
  }

  createIntervention(incidentId: number, technicianId: number, scheduledDate: string | null, notes: string | null): Observable<Intervention> {
    return this.http.post<Intervention>(`${this.baseUrl}/${incidentId}/interventions`, {
      technicianId,
      scheduledDate,
      notes
    });
  }

  updateInterventionStatus(id: number, status: string): Observable<Intervention> {
    return this.http.patch<Intervention>(`/api/interventions/${id}/status`, { status });
  }

  static errorMessage(err: unknown): string {
    if (err instanceof HttpErrorResponse) {
      const body = err.error as ApiError | null;
      if (err.status === 0 || err.status === 504) {
        return 'Cannot reach the Urban Incident service. Is it running on port 8082?';
      }
      if (body?.message) {
        const fields = body.fieldErrors
          ? ' — ' + Object.entries(body.fieldErrors).map(([f, m]) => `${f}: ${m}`).join(', ')
          : '';
        return `${body.message}${fields}`;
      }
      return `Error ${err.status}: ${err.statusText}`;
    }
    return 'Unexpected error';
  }
}
