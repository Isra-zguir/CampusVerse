import { Routes } from '@angular/router';
import { IncidentList } from './pages/incident-list/incident-list';
import { IncidentForm } from './pages/incident-form/incident-form';
import { IncidentDetail } from './pages/incident-detail/incident-detail';
import { Statistics } from './pages/statistics/statistics';

export const routes: Routes = [
  { path: '', component: IncidentList, title: 'Incidents | Smart City' },
  { path: 'new', component: IncidentForm, title: 'Report incident | Smart City' },
  { path: 'statistics', component: Statistics, title: 'Statistics | Smart City' },
  { path: ':id', component: IncidentDetail, title: 'Incident | Smart City' },
  { path: ':id/edit', component: IncidentForm, title: 'Edit incident | Smart City' },
  { path: '**', redirectTo: '' }
];
