import java.util.*;


class Reservation {
    String guestName;
    String roomType;
    int nights;
    String allocatedRoomID;

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


class Service {
    String name;
    double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();


    public void addService(Reservation reservation, Service service) {
        reservationServices.computeIfAbsent(reservation.allocatedRoomID, k -> new ArrayList<>()).add(service);
    }


    public List<Service> getServices(Reservation reservation) {
        return reservationServices.getOrDefault(reservation.allocatedRoomID, new ArrayList<>());
    }


    public double calculateTotalServiceCost(Reservation reservation) {
        return getServices(reservation).stream().mapToDouble(s -> s.cost).sum();
    }


    public void displayAllServices() {
        System.out.println("\n--- Add-On Services for Reservations ---");
        reservationServices.forEach((roomID, services) -> {
            System.out.println("Room ID: " + roomID + " -> " + services);
        });
    }
}

// Step 4: Main Application
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        Reservation res1 = new Reservation("Alice", "Single", 2);
        res1.assignRoomID("S-1a2b3c");

        Reservation res2 = new Reservation("Bob", "Suite", 3);
        res2.assignRoomID("S-4d5e6f");

        List<Reservation> reservations = Arrays.asList(res1, res2);

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        System.out.println("Welcome to Book My Stay - Add-On Service Selection");

        for (Reservation res : reservations) {
            System.out.println("\nReservation: " + res);
            boolean addMore = true;

            while (addMore) {
                System.out.print("Enter service name for " + res.guestName + " (e.g., Breakfast, Spa, Airport Pickup): ");
                String serviceName = sc.nextLine().trim();

                System.out.print("Enter service cost: ");
                double cost = 0;
                try {
                    cost = Double.parseDouble(sc.nextLine().trim());
                    if (cost < 0) {
                        System.out.println("Service cost cannot be negative. Skipping this service.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid cost input. Skipping this service.");
                    continue;
                }

                Service service = new Service(serviceName, cost);
                serviceManager.addService(res, service);

                System.out.print("Add another service for " + res.guestName + "? (yes/no): ");
                String choice = sc.nextLine().trim().toLowerCase();
                if (!choice.equals("yes")) {
                    addMore = false;
                }
            }
        }


        System.out.println("\n--- Reservation Summary with Add-On Services ---");
        for (Reservation res : reservations) {
            System.out.println(res);
            List<Service> services = serviceManager.getServices(res);
            if (!services.isEmpty()) {
                System.out.println("Add-On Services: " + services);
                System.out.println("Total Add-On Cost: $" + serviceManager.calculateTotalServiceCost(res));
            } else {
                System.out.println("No add-on services selected.");
            }
            System.out.println();
        }

        serviceManager.displayAllServices();

        sc.close();
        System.out.println("All add-on services processed successfully.");
    }
}