import { Component, OnInit, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { IncidentService } from '../../services/incident.service';
import {
  INCIDENT_CATEGORIES,
  INCIDENT_STATUSES,
  Incident,
  IncidentFilters,
  PRIORITIES
} from '../../models/incident.model';

@Component({
  selector: 'app-incident-list',
  imports: [RouterLink, FormsModule, DatePipe],
  templateUrl: './incident-list.html'
})
export class IncidentList implements OnInit {
  private readonly incidentService = inject(IncidentService);

  readonly categories = INCIDENT_CATEGORIES;
  readonly statuses = INCIDENT_STATUSES;
  readonly priorities = PRIORITIES;

  readonly incidents = signal<Incident[]>([]);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  filters: IncidentFilters = { category: '', status: '', priority: '' };

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.error.set(null);
    this.incidentService.getAll(this.filters).subscribe({
      next: incidents => {
        this.incidents.set(incidents);
        this.loading.set(false);
      },
      error: err => {
        this.error.set(IncidentService.errorMessage(err));
        this.loading.set(false);
      }
    });
  }

  resetFilters(): void {
    this.filters = { category: '', status: '', priority: '' };
    this.load();
  }
}
