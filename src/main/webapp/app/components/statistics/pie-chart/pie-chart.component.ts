import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';

@Component({
  selector: 'jhi-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnInit {
  chartType: ChartType = 'pie';
  chartLabel: string[] = [];
  chartData: number[] = [];

  chartOptions: ChartOptions = {
    animation: {
      duration: 1000,
      easing: 'easeInOutQuad',
    },

    title: {
      display: true,
      text: 'Başlık',
      fontSize: 25,
    },

    responsive: true,
    legend: {
      display: true,
      position: 'bottom',
      fullWidth: true,
      reverse: false,
    },
  };

  constructor() {}

  chartFilled(): void {
    this.chartLabel.push('Red', 'Blue', 'Yellow', 'Gray', 'Green');

    this.chartData.push(12, 22, 32, 42, 52);
  }

  ngOnInit(): void {
    this.chartFilled();
  }
}
