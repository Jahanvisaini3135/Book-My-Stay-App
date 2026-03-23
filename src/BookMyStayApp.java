import java.util.Scanner;

// Abstract Class
abstract class Room {
    private String roomType;
    private int beds;
    private double price;
    private String size;

    public Room(String roomType, int beds, double price, String size) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
        this.size = size;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public abstract void displayRoomDetails();
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000.0, "Small");
    }

    public void displayRoomDetails() {
        System.out.println("\n--- Single Room ---");
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500.0, "Medium");
    }

    public void displayRoomDetails() {
        System.out.println("\n--- Double Room ---");
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000.0, "Large");
    }

    public void displayRoomDetails() {
        System.out.println("\n--- Suite Room ---");
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize());
        System.out.println("Price: ₹" + getPrice());
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("===== Welcome to Book My Stay App (Version 2.1) =====");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);

        System.out.println("\nEnter room type:");
        System.out.println("1 - Single Room");
        System.out.println("2 - Double Room");
        System.out.println("3 - Suite Room");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Single Room Available: " + singleAvailable);
                break;
            case 2:
                System.out.println("Double Room Available: " + doubleAvailable);
                break;
            case 3:
                System.out.println("Suite Room Available: " + suiteAvailable);
                break;
            default:
                System.out.println("Invalid choice");
        }

        scanner.close();
    }
}