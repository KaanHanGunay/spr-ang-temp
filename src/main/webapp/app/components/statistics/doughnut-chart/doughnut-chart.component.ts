import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';
import { Context } from 'chartjs-plugin-datalabels';

@Component({
  selector: 'jhi-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: ['./doughnut-chart.component.scss'],
})
export class DoughnutChartComponent implements OnInit {
  chartType: ChartType = 'doughnut';
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
      position: 'right',
      fullWidth: true,
      reverse: false,
    },

    layout: {
      padding: {
        left: 50,
        right: 50,
        bottom: 50,
      },
    },

    plugins: {
      datalabels: {
        display: 'auto',
        align: 'bottom',
        backgroundColor: '#ccc',
        borderRadius: 3,
        font: {
          size: 16,
        },
        formatter: this.formatterFunc,
        color: '#fff',
        anchor: 'end',
        textAlign: 'end',
        clamp: true,
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
    return ctx.chart.data.labels![ctx.dataIndex] + ' ' + ((100 * value) / sum).toFixed(2) + '%';
  }

  chartFilled(): void {
    this.chartLabel.push('Red', 'Blue', 'Yellow', 'Gray', 'Green');
    this.chartData.push(12, 22, 32, 42, 52);
  }

  ngOnInit(): void {
    this.chartFilled();
  }
}
