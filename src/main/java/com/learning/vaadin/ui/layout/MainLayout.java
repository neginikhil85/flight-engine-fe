package com.learning.vaadin.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        Image logoImage = new Image("images/indigo-logo.svg", "logo");

        H1 title = new H1("FE | Flight Engine");

        DrawerToggle drawerToggle = new DrawerToggle();
        HorizontalLayout header = new HorizontalLayout(drawerToggle, logoImage, title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();

        header.setClassName("app-header");

        addToNavbar(header);
    }

    private void createDrawer() {
        H5 categories = new H5("Categories");
        addToDrawer(new VerticalLayout(categories, getSideNav()));
    }

    private SideNav getSideNav() {
        SideNav nav = new SideNav();
        nav.addClassName("app-drawer");

        nav.addItem(new SideNavItem("Dashboard", "/dashboard", VaadinIcon.DASHBOARD.create()));
        nav.addItem(new SideNavItem("Flight Inquiry", "/api/flight-inquiry", VaadinIcon.FLIGHT_TAKEOFF.create()));

        SideNavItem events = new SideNavItem("Events");
        events.setPrefixComponent(VaadinIcon.EXCHANGE.create());
        events.addItem(new SideNavItem("Delays", "/ws/delay"));
        events.addItem(new SideNavItem("Diversions", "/ws/diversion"));
        events.addItem(new SideNavItem("Door Close", "/ws/door-close"));
//        events.addItem(new SideNavItem("Estimated Times", "/ws/estimated-times"));
//        events.addItem(new SideNavItem("Actual Times", "/ws/actual-times"));
        events.addItem(new SideNavItem("Equipment", "/ws/equipment"));
        events.addItem(new SideNavItem("Flight Cancel", "/ws/flight-cancel"));
        events.addItem(new SideNavItem("Flight Return", "/ws/flight-return"));
        events.addItem(new SideNavItem("Flight Delete", "/ws/flight-delete"));
        events.addItem(new SideNavItem("Operation Status", "/ws/operation-status"));
        events.addItem(new SideNavItem("Gate Change", "/ws/gate-change"));
        events.addItem(new SideNavItem("Terminal", "/ws/terminal"));
        events.addItem(new SideNavItem("InBlock Estimated Time", "/ws/in-block-estimated-time"));
        events.addItem(new SideNavItem("OffBlock Estimated Time", "/ws/off-block-actual-time"));
        events.addItem(new SideNavItem("TakeOff Estimated Time", "/ws/takeoff-estimated-time"));
        events.addItem(new SideNavItem("Landing Estimated Time", "/ws/landing-estimated-time"));
        events.addItem(new SideNavItem("InBlock Actual Time", "/ws/in-block-actual-time"));
        events.addItem(new SideNavItem("Landing Actual Time", "/ws/landing-actual-time"));
        events.addItem(new SideNavItem("OffBlock Actual Time", "/ws/off-block-actual-time"));
        events.addItem(new SideNavItem("TakeOff Actual Time", "/ws/takeoff-actual-time"));

        // Adjusting only the drawer width without impacting other styles
        events.getElement().addEventListener("click", listener -> {
            if (events.isExpanded()) {
                nav.addClassName("side-nav-bar-item-expanded");
            } else {
                nav.removeClassName("side-nav-bar-item-expanded");
            }
        });

        nav.addItem(events);
        return nav;
    }
}
