import {Component, OnInit, ViewChild} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {ChartConfiguration, ChartData, ChartType} from "chart.js";

import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import {StatisticService} from "../../service/statisticService";
import {BasicStatisticData} from "../../model/basicStatisticData";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  @ViewChild(BaseChartDirective) lineChart: BaseChartDirective | undefined;

  basicStatisticData: BasicStatisticData | undefined;
  pieChartData: ChartData<'pie', number[], string | string[]> | undefined;
  public lineChartData: ChartConfiguration['data'] | undefined;

  constructor(private statisticService: StatisticService) {
  }

  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
      datalabels: {
        formatter: (value, ctx) => {
          if (ctx.chart.data.labels) {
            return ctx.chart.data.labels[ctx.dataIndex];
          }
        }
      },
    }
  };

  public pieChartType: ChartType = 'pie';

  public pieChartPlugins = [DataLabelsPlugin];

  public lineChartOptions: ChartConfiguration['options'] = {
    elements: {
      line: {
        tension: 0.5
      }
    },
    scales: {
      x: {},
      'y-axis-0':
        {
          position: 'left',
        }
    },

    plugins: {
      legend: {display: true},
    }
  };

  public lineChartType: ChartType = 'line';

  testee: number[] = [];

  ngOnInit(): void {
    this.statisticService.getBasicStatisticData()
      .subscribe((response) => {
        this.basicStatisticData = response;
      });

    this.statisticService.getCountOfWinnerOnLeftAndRightSide()
      .subscribe((response) => {
        this.pieChartData = {
          labels: ['left', 'right'],
          datasets: [{
            data: [response.winnerOnLeftSideCount, response.winnerOnRightSideCount]
          }]
        };
      });

    this.statisticService.getDurationStatistic()
      .subscribe((response) => {
        this.lineChartData = {
          datasets: [
            {
              data: [5, 2, 4, 5, 6, 7, 3, 6, 3, 6],
              label: 'Time to vote',
              fill: 'origin',
            }
          ],
          labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '> 10']
        };
      });
  }
}
