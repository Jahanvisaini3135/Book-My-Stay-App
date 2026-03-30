import java.util.*;


class Reservation {
    String guestName;
    String roomType;
    int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType + ", Nights: " + nights;
    }
}


class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();


    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }


    public void displayPendingRequests() {
        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests in the queue.");
        } else {
            System.out.println("\n--- Pending Booking Requests ---");
            for (Reservation r : requestQueue) {
                System.out.println(r);
            }
        }
    }


    public Reservation processNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("Welcome to Book My Stay - Booking Request Queue");
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
            bookingQueue.addRequest(reservation);

            System.out.print("Do you want to add another booking request? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (!choice.equals("yes")) {
                continueInput = false;
            }
        }


        bookingQueue.displayPendingRequests();

        sc.close();
        System.out.println("\nAll booking requests are queued for allocation.");
    }
}