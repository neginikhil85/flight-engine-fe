package com.learning.vaadin.ui.view;

import com.github.appreciated.apexcharts.ApexCharts;
import com.learning.vaadin.ui.component.chart.DonutChart;
import com.learning.vaadin.ui.component.chart.bar.HorizontalBarChart;
import com.learning.vaadin.ui.component.chart.bar.VerticalBarChart;
import com.learning.vaadin.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        ApexCharts donut = new DonutChart().build();
        ApexCharts charts = new HorizontalBarChart().build();
        ApexCharts vertical = new VerticalBarChart().build();

        donut.setHeight("300px");
        charts.setHeight("300px");
        vertical.setHeight("300px");
        add(donut, charts, vertical);
    }

}
