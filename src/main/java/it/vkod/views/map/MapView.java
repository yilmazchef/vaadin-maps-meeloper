package it.vkod.views.map;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.Extent;
import com.vaadin.flow.component.map.configuration.Feature;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.constraints.NotBlank;

@PageTitle("Map")
@Route(value = "map")
@RouteAlias(value = "")
public class MapView extends VerticalLayout {
    // For Vaadin 23.1, use Coordinate.fromLonLat to create coordinates
    // from longitude and latitude
    private static final City BERLIN = new City("Berlin", new Coordinate(13.404954, 52.520008));
    private static final City HONG_KONG = new City("Hong Kong", new Coordinate(114.162813, 22.279328));
    private static final City MOSCOW = new City("Moscow", new Coordinate(37.617298, 55.755825));
    private static final City NEW_YORK = new City("New York", new Coordinate(-74.005974, 40.712776));
    private static final City RIO = new City("Rio de Janeiro", new Coordinate(-43.2093727, -22.9110137));
    private static final City TOKYO = new City("Tokyo", new Coordinate(139.6917064, 35.6894875));
    private static final City ZURICH = new City("Zurich", new Coordinate(8.541694, 47.376887));
    private static final City BRUSSELS = new City("Brussels", new Coordinate(4.35171, 50.85034));
    private static final City PARIS = new City("Paris", new Coordinate(2.35222, 48.85661));
    private static final City LONDON = new City("London", new Coordinate(-0.1277583, 51.5073509));
    private static final City MADRID = new City("Madrid", new Coordinate(-3.7037902, 40.4167754));
    private static final City STOCKHOLM = new City("Stockholm", new Coordinate(18.0685808, 59.3293235));
    private static final City HELSINKI = new City("Helsinki", new Coordinate(24.9383791, 60.1698557));
    private static final City OSLO = new City("Oslo", new Coordinate(10.7522454, 59.9138688));
    private static final City COPENHAGEN = new City("Copenhagen", new Coordinate(12.5683372, 55.6760968));
    private static final City AMSTERDAM = new City("Amsterdam", new Coordinate(4.89797550561798, 52.3745403));
    private static final City BERNE = new City("Berne", new Coordinate(7.4458, 46.9480));
    private static final City PRAGUE = new City("Prague", new Coordinate(14.4378, 50.0755));
    private static final City VIENNA = new City("Vienna", new Coordinate(16.3738, 48.2082));
    private static final City BUDAPEST = new City("Budapest", new Coordinate(19.0402, 47.4979));
    private static final City ROME = new City("Rome", new Coordinate(12.4964, 41.9028));
    private static final City ATHENS = new City("Athens", new Coordinate(23.7275, 37.9838));
    private static final City ISTANBUL = new City("Istanbul", new Coordinate(28.9784, 41.0082));
    private static final City LUXEMBOURG = new City("Luxembourg", new Coordinate(6.1296, 49.8153));
    private static final City BUCHAREST = new City("Bucharest", new Coordinate(26.1025, 44.4268));
    private static final City WARSAW = new City("Warsaw", new Coordinate(21.0122, 52.2297));
    private static final City KIEV = new City("Kiev", new Coordinate(30.5234, 50.4501));
    private static final City MINSK = new City("Minsk", new Coordinate(27.5667, 53.9000));
    private static final City TALLINN = new City("Tallinn", new Coordinate(24.7536, 59.4369));
    private static final City RIGA = new City("Riga", new Coordinate(24.1052, 56.9496));
    private static final City VILNIUS = new City("Vilnius", new Coordinate(25.2799, 54.6872));
    private static final City SOFIA = new City("Sofia", new Coordinate(23.3219, 42.6977));
    private static final City BELGRADE = new City("Belgrade", new Coordinate(20.4489, 44.7866));
    private static final City LUBLIN = new City("Lublin", new Coordinate(22.5667, 51.2500));
    private static final City ZELE = new City("Zele", new Coordinate(4.0382604525272905, 51.06365156605191));

    private static final Logger LOGGER = Logger.getLogger(MapView.class.getName());

    private static final List<City> CITIES = List.of(
            BERLIN, HONG_KONG, MOSCOW, NEW_YORK, RIO, TOKYO, ZURICH, BRUSSELS, PARIS, LONDON, MADRID, STOCKHOLM,
            HELSINKI, OSLO, COPENHAGEN, AMSTERDAM, BERNE, PRAGUE, VIENNA, BUDAPEST, ROME, ATHENS, ISTANBUL, LUXEMBOURG,
            BUCHAREST, WARSAW, KIEV, MINSK, TALLINN, RIGA, VILNIUS, SOFIA, BELGRADE, LUBLIN, ZELE);

    private final Map map = new Map();
    private final TextArea mapClickInfo = new TextArea("Map Click Event");

    public MapView() {

        addClassName("map-view");

        // For Vaadin 23.1, use Coordinate.fromLonLat to create coordinates
        // from longitude and latitude
        Coordinate zeleLocation01 = new Coordinate(4.0382604525272905, 51.06365156605191);
        map.setCenter(zeleLocation01);
        map.setZoom(12);
        add(map);

        // Setup text areas for logging event data

        mapClickInfo.setReadOnly(true);
        mapClickInfo.setWidthFull();
        mapClickInfo.addThemeName("monospace");
        add(mapClickInfo);

        // Add markers for cities
        HashMap<Feature, City> cityLookup = new HashMap<>();
        CITIES.forEach(city -> {
            MarkerFeature cityMarker = new MarkerFeature(city.coordinates);
            map.getFeatureLayer().addFeature(cityMarker);
            // Store relation between cities and markers in a hash map
            cityLookup.put(cityMarker, city);
        });

        map.addViewMoveEndEventListener(e -> {
            Coordinate center = e.getCenter();
            Extent extent = e.getExtent();
            String info = "";
            info += String.format("Center = { x: %s, y: %s }%n", center.getX(), center.getY());
            info += String.format("Zoom   = %s%n", e.getZoom());
            info += String.format("Extent = { left: %s, top: %s,%n", extent.getMinX(), extent.getMinY());
            info += String.format("           right: %s, bottom: %s }", extent.getMaxX(), extent.getMaxY());
            // Notification.show(info, 1000, Notification.Position.BOTTOM_CENTER);
            LOGGER.info(info);
        });

        // Add a click listener to the map
        map.addClickEventListener(e -> {
            Coordinate coordinates = e.getCoordinate();
            String info = String.format("Coordinates = { x: %s, y: %s }", coordinates.getX(), coordinates.getY());
            mapClickInfo.setValue(info);
        });

        // Add click listener for markers
        map.addFeatureClickListener(e -> {
            MarkerFeature feature = (MarkerFeature) e.getFeature();
            Coordinate coordinates = feature.getCoordinates();
            // Get city entity for event marker,
            // see remaining example on how the markers are set up
            City city = cityLookup.get(feature);
            String info = "";
            info += String.format("City        = %s%n", city.getName());
            info += String.format("Coordinates = { x: %s, y: %s }", coordinates.getX(), coordinates.getY());
            navigateToGoogleMapsByCoordinates(
                    new Double[] {
                            ZELE.coordinates.getY(),
                            ZELE.coordinates.getX()
                    },
                    new Double[] {
                            coordinates.getY(),
                            coordinates.getX()
                    });
            // featureClickInfo.setValue(info);
        });
        setSpacing(false);
        setPadding(false);
    }

    private static class City {
        private final String name;
        private final Coordinate coordinates;

        public String getName() {
            return name;
        }

        public Coordinate getCoordinates() {
            return coordinates;
        }

        public City(String name, Coordinate coordinates) {
            this.name = name;
            this.coordinates = coordinates;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            City other = (City) obj;
            if (coordinates == null) {
                if (other.coordinates != null)
                    return false;
            } else if (!coordinates.equals(other.coordinates))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return String.valueOf(this.getCoordinates());
        }

    }

    // Directions URL:
    // https://www.google.com/maps/dir/51.0633279,4.0366726/51.068288,4.052609/@51.0652076,4.0370363
    // https://www.google.com/maps/dir/from/to/@to

    private void navigateToGoogleMapsByCoordinates(Double[] from, Double[] to) {
        final var directionsUrl = "https://www.google.com/maps/dir" + "/" + from[0] + "," + from[1] + "/" + to[0] + ","
                + to[1] + "/@" + to[0] + "," + to[1];
        UI.getCurrent().getPage().executeJs("window.open($0, '_blank')", directionsUrl);
    }

}
