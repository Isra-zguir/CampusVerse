import { Component, OnInit, inject, signal } from '@angular/core';

import { IncidentService } from '../../services/incident.service';
import { Statistics as Stats } from '../../models/incident.model';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.html'
})
export class Statistics implements OnInit {
  private readonly incidentService = inject(IncidentService);

  readonly stats = signal<Stats | null>(null);
  readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.incidentService.statistics().subscribe({
      next: s => this.stats.set(s),
      error: err => this.error.set(IncidentService.errorMessage(err))
    });
  }

  entries(map: Record<string, number> | undefined): [string, number][] {
    return Object.entries(map ?? {});
  }
}
