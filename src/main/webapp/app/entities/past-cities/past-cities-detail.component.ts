import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPastCities } from 'app/shared/model/past-cities.model';

@Component({
  selector: 'jhi-past-cities-detail',
  templateUrl: './past-cities-detail.component.html',
})
export class PastCitiesDetailComponent implements OnInit {
  pastCities: IPastCities | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pastCities }) => (this.pastCities = pastCities));
  }

  previousState(): void {
    window.history.back();
  }
}
