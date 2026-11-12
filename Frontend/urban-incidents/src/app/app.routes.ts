import { Routes } from '@angular/router';
import { IncidentList } from './pages/incident-list/incident-list';
import { IncidentForm } from './pages/incident-form/incident-form';
import { IncidentDetail } from './pages/incident-detail/incident-detail';
import { WorkOrders } from './pages/work-orders/work-orders';
import { Interventions } from './pages/interventions/interventions';
import { Technicians } from './pages/technicians/technicians';
import { Statistics } from './pages/statistics/statistics';

export const routes: Routes = [
  { path: '', component: IncidentList, title: 'Incidents | Smart City' },
  { path: 'new', component: IncidentForm, title: 'Report incident | Smart City' },
  { path: 'work-orders', component: WorkOrders, title: 'Work orders | Smart City' },
  { path: 'interventions', component: Interventions, title: 'Interventions | Smart City' },
  { path: 'technicians', component: Technicians, title: 'Technicians | Smart City' },
  { path: 'statistics', component: Statistics, title: 'Statistics | Smart City' },
  { path: ':id', component: IncidentDetail, title: 'Incident | Smart City' },
  { path: '**', redirectTo: '' }
];
