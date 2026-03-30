import java.util.*;


class Room {
    String type;
    int capacity;
    double pricePerNight;
    List<String> amenities;

    public Room(String type, int capacity, double pricePerNight, List<String> amenities) {
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return "Room Type: " + type + ", Capacity: " + capacity +
                ", Price/Night: $" + pricePerNight +
                ", Amenities: " + amenities;
    }
}


class Inventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();
    private Map<String, Room> roomDetails = new HashMap<>();

    public Inventory() {

        roomDetails.put("Single", new Room("Single", 1, 50.0, Arrays.asList("WiFi", "TV")));
        roomDetails.put("Double", new Room("Double", 2, 90.0, Arrays.asList("WiFi", "TV", "Mini Fridge")));
        roomDetails.put("Suite", new Room("Suite", 4, 200.0, Arrays.asList("WiFi", "TV", "Mini Fridge", "Jacuzzi")));

        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 0); // Suite currently unavailable
    }


    public List<Room> searchAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (String type : roomDetails.keySet()) {
            if (roomAvailability.getOrDefault(type, 0) > 0) {
                availableRooms.add(roomDetails.get(type));
            }
        }
        return availableRooms;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventory inventory = new Inventory();

        System.out.println("Welcome to Book My Stay - Room Search");
        System.out.print("Do you want to view available rooms? (yes/no): ");
        String input = sc.nextLine().trim().toLowerCase();

        if (input.equals("yes")) {
            List<Room> availableRooms = inventory.searchAvailableRooms();

            if (availableRooms.isEmpty()) {
                System.out.println("Sorry, no rooms are currently available.");
            } else {
                System.out.println("\n--- Available Rooms ---");
                availableRooms.forEach(System.out::println);
            }
        } else {
            System.out.println("Search cancelled. Thank you!");
        }

        sc.close();
    }
}