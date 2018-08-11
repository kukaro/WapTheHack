// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ["03시", "06시", "09시", "12시", "15시", "18시", "21시","24시"],
    datasets: [{
      label: "3시간강수량",
      lineTension: 0.3,
      backgroundColor: "rgba(2,117,216,0.2)",
      borderColor: "rgba(2,117,216,1)",
      pointRadius: 5,
      pointBackgroundColor: "rgba(2,117,216,1)",
      pointBorderColor: "rgba(255,255,255,0.8)",
      pointHoverRadius: 5,
      pointHoverBackgroundColor: "rgba(2,117,216,1)",
      pointHitRadius: 50,
      pointBorderWidth: 2,
      data: []/*[50, 60, 70, 65, 80, 87, 90, 93]*/,
    },
        {
            label: "강수확률",
            lineTension: 0.3,
            backgroundColor: 'rgba(255,0,0,0.1)',
            borderColor: "rgba(255,0,0,1)",
            pointRadius: 5,
            pointBackgroundColor: "rgba(255,0,0,1)",
            pointBorderColor: "rgba(255,255,255,0.8)",
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(255,0,0,1)",
            pointHitRadius: 50,
            pointBorderWidth: 2,
            data: []/*[50, 60, 70, 65, 80, 87, 90, 93]*/,
        }],
  },
  options: {
    scales: {
      xAxes: [{
        scaleLabel:{
          display:true,
            labelString:'시간',
            labelColor:'red'
        },
        time: {
          unit: 'date'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: 8
        }
      }],
      yAxes: [{
          scaleLabel:{
              display:true,
              labelString:'3시간 강수량(mm)/강수확률(%)'
          },
        ticks: {
          min: 0,
          max: 100,
          maxTicksLimit: 5
        },
        gridLines: {
          color: "rgba(0, 0, 0, .125)",
        }
      }],
    },
    legend: {
      display: true,
        position:'top',
        labels:{
        boxWidth:80,
            fontColor:'black'
        }
    }
  }
});
