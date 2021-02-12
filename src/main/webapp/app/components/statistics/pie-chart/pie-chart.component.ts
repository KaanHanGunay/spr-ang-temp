import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';
import 'chartjs-plugin-datalabels';
import { Context } from 'chartjs-plugin-datalabels';

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

    plugins: {
      datalabels: {
        display: true,
        align: 'bottom',
        backgroundColor: '#ccc',
        borderRadius: 3,
        font: {
          size: 18,
        },
        formatter: this.formatterFunc,
        color: '#fff',
      },
    },
  };

  constructor() {}

  formatterFunc(value: number, ctx: Context): string {
    let sum = 0;
    const dataArr = ctx.dataset.data;

    if (Array.isArray(dataArr)) {
      dataArr.forEach((data: any) => {
        sum += +data;
      });
    }
    return ((100 * value) / sum).toFixed(2) + '%';
  }

  chartFilled(): void {
    this.chartLabel.push('Red', 'Blue', 'Yellow', 'Gray', 'Green');
    this.chartData.push(12, 22, 32, 42, 52);
  }

  ngOnInit(): void {
    this.chartFilled();
  }
}
