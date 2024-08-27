package com.learning.vaadin.ui.component.chart;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.FillBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;

public class DonutChart extends ApexChartsBuilder {

    public DonutChart() {
        withChart(ChartBuilder.get().withType(Type.DONUT).build())
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.RIGHT)
                        .build())
                .withSeries(44.0, 55.0, 41.0, 17.0, 15.0)
                .withFill(FillBuilder.get().withType("red").build())
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withLegend(LegendBuilder.get()
                                        .withPosition(Position.BOTTOM)
                                        .build())
                                .build())
                        .build());
    }
}