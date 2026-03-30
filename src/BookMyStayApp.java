import java.util.*;

// Step 1: Reservation class
class Reservation {
    String guestName;
    String roomType;
    int nights;
    String allocatedRoomID; // Assigned during allocation

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.allocatedRoomID = null;
    }

    public void assignRoomID(String roomID) {
        this.allocatedRoomID = roomID;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Nights: " + nights +
                (allocatedRoomID != null ? ", Room ID: " + allocatedRoomID : ", Room ID: Not Assigned");
    }
}

// Step 2: Inventory Service
class InventoryService {
    private Map<String, Integer> roomAvailability = new HashMap<>();
    private Map<String, Set<String>> allocatedRoomIDs = new HashMap<>();

    public InventoryService() {
        // Initial inventory
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);

        // Initialize allocated room sets
        allocatedRoomIDs.put("Single", new HashSet<>());
        allocatedRoomIDs.put("Double", new HashSet<>());
        allocatedRoomIDs.put("Suite", new HashSet<>());
    }

    // Check if room type is available
    public boolean isAvailable(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0) > 0;
    }

    // Allocate a room ID and decrement inventory
    public String allocateRoom(String roomType) {
        if (!isAvailable(roomType)) return null;

        // Generate unique room ID
        String roomID;
        do {
            roomID = roomType.substring(0, 1).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 6);
        } while (allocatedRoomIDs.get(roomType).contains(roomID));

        // Record allocation
        allocatedRoomIDs.get(roomType).add(roomID);
        roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);

        return roomID;
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        roomAvailability.forEach((type, count) -> System.out.println(type + ": " + count + " available"));
    }
}

// Step 3: Booking Service
class BookingService {
    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void confirmReservations(Queue<Reservation> bookingQueue) {
        System.out.println("\n--- Confirming Reservations ---");
        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.poll();
            if (inventory.isAvailable(reservation.roomType)) {
                String roomID = inventory.allocateRoom(reservation.roomType);
                reservation.assignRoomID(roomID);
                System.out.println("Reservation confirmed: " + reservation);
            } else {
                System.out.println("Sorry, no " + reservation.roomType + " rooms available for " + reservation.guestName);
            }
        }
    }
}

// Step 4: Main Application
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        Queue<Reservation> bookingQueue = new LinkedList<>();
        System.out.println("Welcome to Book My Stay - Reservation Confirmation & Room Allocation");

        boolean continueInput = true;
        while (continueInput) {
            System.out.print("\nEnter guest name: ");
            String guestName = sc.nextLine().trim();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = sc.nextLine().trim();

            System.out.print("Enter number of nights: ");
            int nights = 0;
            try {
                nights = Integer.parseInt(sc.nextLine().trim());
                if (nights <= 0) {
                    System.out.println("Number of nights must be greater than zero. Request skipped.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number of nights. Request skipped.");
                continue;
            }

            Reservation reservation = new Reservation(guestName, roomType, nights);
            bookingQueue.offer(reservation);

            System.out.print("Do you want to add another booking request? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (!choice.equals("yes")) {
                continueInput = false;
            }
        }

        inventory.displayInventory();

        // Confirm reservations and allocate rooms
        bookingService.confirmReservations(bookingQueue);

        inventory.displayInventory();

        sc.close();
        System.out.println("\nAll reservations processed successfully.");
    }
}