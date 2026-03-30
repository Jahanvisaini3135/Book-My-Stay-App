import java.util.*;


class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}


class Room {
    String type;
    int available;

    public Room(String type, int available) throws InvalidBookingException {
        if (available < 0) {
            throw new InvalidBookingException("Room availability cannot be negative");
        }
        this.type = type;
        this.available = available;
    }
}


class Reservation {
    String guestName;
    String roomType;
    int nights;

    public Reservation(String guestName, String roomType, int nights) throws InvalidBookingException {
        if (guestName == null || guestName.isBlank()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }
        if (roomType == null || roomType.isBlank()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }
        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than zero");
        }
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    @Override
    public String toString() {
        return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + ", Nights: " + nights + "]";
    }
}


class BookingService {
    private Map<String, Room> inventory;

    public BookingService(Map<String, Room> inventory) {
        this.inventory = inventory;
    }

    public void validateAndBook(Reservation reservation) throws InvalidBookingException {
        Room room = inventory.get(reservation.roomType);


        if (room == null) {
            throw new InvalidBookingException("Invalid room type: " + reservation.roomType);
        }
        if (room.available <= 0) {
            throw new InvalidBookingException("No available rooms of type: " + reservation.roomType);
        }


        room.available--;
        System.out.println("Booking confirmed: " + reservation);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        inventory.forEach((type, room) -> System.out.println(type + " -> Available: " + room.available));
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {

            Map<String, Room> inventory = new HashMap<>();
            inventory.put("Single", new Room("Single", 2));
            inventory.put("Double", new Room("Double", 1));
            inventory.put("Suite", new Room("Suite", 0)); // Suite unavailable

            BookingService bookingService = new BookingService(inventory);

            boolean continueBooking = true;
            while (continueBooking) {
                System.out.println("\n--- New Booking ---");
                System.out.print("Enter guest name: ");
                String name = sc.nextLine().trim();
                System.out.print("Enter room type (Single/Double/Suite): ");
                String type = sc.nextLine().trim();
                System.out.print("Enter number of nights: ");
                int nights = 0;

                try {
                    nights = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number of nights. Please enter a valid integer.");
                    continue;
                }

                try {
                    Reservation reservation = new Reservation(name, type, nights);
                    bookingService.validateAndBook(reservation);
                } catch (InvalidBookingException e) {
                    System.out.println("Booking failed: " + e.getMessage());
                }

                bookingService.displayInventory();

                System.out.print("\nDo you want to make another booking? (yes/no): ");
                String choice = sc.nextLine().trim().toLowerCase();
                if (!choice.equals("yes")) {
                    continueBooking = false;
                }
            }

        } catch (InvalidBookingException e) {
            System.out.println("Error initializing system: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Booking system terminated safely.");
        }
    }
}