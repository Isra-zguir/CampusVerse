import { Component, OnInit, computed, inject, input, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';

import { IncidentService } from '../../services/incident.service';
import { Incident, Intervention, InterventionStatus } from '../../models/incident.model';

@Component({
  selector: 'app-incident-detail',
  imports: [RouterLink, FormsModule, DatePipe],
  templateUrl: './incident-detail.html'
})
export class IncidentDetail implements OnInit {
  private readonly incidentService = inject(IncidentService);
  private readonly router = inject(Router);

  readonly id = input.required<string>();

  readonly incident = signal<Incident | null>(null);
  readonly interventions = signal<Intervention[]>([]);
  readonly error = signal<string | null>(null);
  readonly message = signal<string | null>(null);
  readonly busy = signal(false);

  reason = '';
  technicianId: number | null = null;
  scheduledDate = '';
  notes = '';

  readonly canEdit = computed(() => {
    const s = this.incident()?.status;
    return s === 'REPORTED' || s === 'PENDING_INFO';
  });
  readonly canValidate = computed(() => {
    const s = this.incident()?.status;
    return s === 'REPORTED' || s === 'PENDING_INFO';
  });
  readonly canWorkOrder = computed(() => {
    const s = this.incident()?.status;
    return s === 'VALIDATED' || s === 'IN_PROGRESS';
  });

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    const id = Number(this.id());
    this.incidentService.getById(id).subscribe({
      next: i => this.incident.set(i),
      error: err => this.error.set(IncidentService.errorMessage(err))
    });
    this.incidentService.getInterventions(id).subscribe({
      next: list => this.interventions.set(list),
      error: () => this.interventions.set([])
    });
  }

  validate(): void {
    this.run(this.incidentService.validate(this.incidentId), 'Incident validated');
  }

  reject(): void {
    if (!this.reason.trim()) {
      this.error.set('Enter a rejection reason');
      return;
    }
    this.run(this.incidentService.reject(this.incidentId, this.reason.trim()), 'Incident rejected');
  }

  requestInfo(): void {
    this.run(this.incidentService.requestInfo(this.incidentId, this.reason.trim()), 'Additional information requested');
  }

  close(): void {
    this.run(this.incidentService.close(this.incidentId), 'Incident closed');
  }

  classify(): void {
    this.run(this.incidentService.classify(this.incidentId), 'Category suggested by the classifier');
  }

  prioritize(): void {
    this.run(this.incidentService.prioritize(this.incidentId), 'Priority suggested by the classifier');
  }

  deleteIncident(): void {
    if (confirm('Delete this incident permanently?')) {
      this.busy.set(true);
      this.incidentService.delete(this.incidentId).subscribe({
        next: () => this.router.navigate(['/']),
        error: err => this.fail(err)
      });
    }
  }

  createWorkOrder(): void {
    if (!this.technicianId) {
      this.error.set('Enter a technician ID');
      return;
    }
    this.busy.set(true);
    this.clearAlerts();
    this.incidentService.createIntervention(
      this.incidentId,
      this.technicianId,
      this.scheduledDate || null,
      this.notes || null
    ).subscribe({
      next: () => {
        this.message.set('Work order created');
        this.technicianId = null;
        this.scheduledDate = '';
        this.notes = '';
        this.busy.set(false);
        this.reload();
      },
      error: err => this.fail(err)
    });
  }

  setInterventionStatus(item: Intervention, status: InterventionStatus): void {
    this.run(this.incidentService.updateInterventionStatus(item.id, status), `Intervention marked ${status}`);
  }

  private get incidentId(): number {
    return Number(this.id());
  }

  private run(action: Observable<unknown>, successMessage: string): void {
    this.busy.set(true);
    this.clearAlerts();
    action.subscribe({
      next: () => {
        this.message.set(successMessage);
        this.busy.set(false);
        this.reload();
      },
      error: err => this.fail(err)
    });
  }

  private fail(err: unknown): void {
    this.error.set(IncidentService.errorMessage(err));
    this.busy.set(false);
  }

  private clearAlerts(): void {
    this.error.set(null);
    this.message.set(null);
  }
}
