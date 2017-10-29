/* 
 * Semester: A171
 * Course: STIW3054
 * Group: A
 * Task: Issue No.3, Week 5
 * Matric Num: 240448
 * Name: Lim Siang Yee
 *
 */
// Reference: https://knowm.org/how-to-make-real-time-charts-in-java/

package Week06_01;

import java.util.Random;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class LineChart {
    
    public static void main(String[] args) throws Exception {
        
        double phase = 0;
        double[][] initdata = getData(phase);

        final XYChart chart = QuickChart.getChart("STIW3054: Chart for 1 mil Random Numbers", "Counter", "Number", "val", initdata[0], initdata[1]);
        chart.getStyler().setLegendVisible(false);

        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();

        while (true) {
            // phase = The tick unit, space between tickmarks
            phase +=  0.5;
            
            // 1 phase will display 100 numbers
            // 1 mil numbers will take 500,000 phases
            if (phase > 500000){
                break;
            }
            Thread.sleep(500);

            final double[][] data = getData(phase);

            chart.updateXYSeries("val", data[0], data[1], null);
            sw.repaintChart();
        }
    }

    private static double[][] getData(double phase) {
        int min = 0, max = 100;
        Random rn = new Random();
        double[] xData = new double[100];
        double[] yData = new double[100];
        for (int i = 0; i < xData.length; i++) {
            double counter = phase + (2 * Math.PI / xData.length * i);
            int randomNum = rn.nextInt(max - min + 1) + min;
            xData[i] = counter;
            yData[i] = randomNum;
        }
        return new double[][]{xData, yData};
    }
}