import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Funn on 2018/11/23.
 */
public class DelegatingVehicleTracker {

    private final Map<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("no such vehicle: " + id);
        }
    }

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "test");
        Map<Integer, String> unmodifiable = Collections.unmodifiableMap(map);
        Map<Integer, String> map2 = new HashMap<>(unmodifiable);
        map2.put(1, "anotherValue"); // 这里不会抛异常，虽然map2是基于一个unmodifiableMap创建的

    }
}
