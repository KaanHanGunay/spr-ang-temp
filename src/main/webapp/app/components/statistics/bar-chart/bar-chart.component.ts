import { Component, OnInit } from '@angular/core';
import { ChartOptions } from 'chart.js';

@Component({
  selector: 'jhi-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss'],
})
export class BarChartComponent implements OnInit {
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
    layout: {
      padding: {
        left: 50,
        right: 50,
        bottom: 50,
      },
    },
    scales: {
      yAxes: [
        {
          ticks: {
            fontSize: 10,
          },
        },
      ],
    },
  };

  constructor() {}

  ngOnInit(): void {
    this.chartFilled();
  }

  chartFilled(): void {
    this.chartLabel.push('Red', 'Blue', 'Yellow', 'Gray', 'Green');
    this.chartData.push(12, 22, 32, 42, 52);
  }
}
