import { Component, OnInit, inject, input, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { IncidentService } from '../../services/incident.service';
import { INCIDENT_CATEGORIES, IncidentCategory, IncidentRequest, PRIORITIES, Priority } from '../../models/incident.model';

@Component({
  selector: 'app-incident-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './incident-form.html'
})
export class IncidentForm implements OnInit {
  private readonly incidentService = inject(IncidentService);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  readonly id = input<string>();

  readonly categories = INCIDENT_CATEGORIES;
  readonly priorities = PRIORITIES;
  readonly error = signal<string | null>(null);
  readonly saving = signal(false);

  readonly form = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(150)]],
    description: ['', Validators.maxLength(2000)],
    photoUrl: ['', Validators.maxLength(500)],
    location: ['', [Validators.required, Validators.maxLength(200)]],
    neighborhood: ['', Validators.maxLength(120)],
    category: ['POTHOLE' as IncidentCategory, Validators.required],
    priority: ['MEDIUM' as Priority, Validators.required],
    reporterId: [1, [Validators.required, Validators.min(1)]]
  });

  get isEdit(): boolean {
    return !!this.id();
  }

  ngOnInit(): void {
    if (this.isEdit) {
      this.incidentService.getById(Number(this.id())).subscribe({
        next: i => this.form.patchValue({
          title: i.title,
          description: i.description ?? '',
          photoUrl: i.photoUrl ?? '',
          location: i.location,
          neighborhood: i.neighborhood ?? '',
          category: i.category,
          priority: i.priority,
          reporterId: i.reporterId
        }),
        error: err => this.error.set(IncidentService.errorMessage(err))
      });
    }
  }

  invalid(name: string): boolean {
    const c = this.form.get(name);
    return !!c && c.invalid && (c.touched || c.dirty);
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const v = this.form.getRawValue();
    const request: IncidentRequest = {
      title: v.title!.trim(),
      description: v.description || null,
      photoUrl: v.photoUrl || null,
      location: v.location!.trim(),
      neighborhood: v.neighborhood || null,
      category: v.category!,
      priority: v.priority!,
      reporterId: Number(v.reporterId)
    };

    this.saving.set(true);
    this.error.set(null);
    const save$ = this.isEdit
      ? this.incidentService.update(Number(this.id()), request)
      : this.incidentService.create(request);

    save$.subscribe({
      next: saved => this.router.navigate(['/', saved.id]),
      error: err => {
        this.error.set(IncidentService.errorMessage(err));
        this.saving.set(false);
      }
    });
  }
}
