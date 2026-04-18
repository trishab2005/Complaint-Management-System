package com.example;

import com.example.entity.Complaint;
import com.example.service.ComplaintService;
import com.example.service.impl.ComplaintServiceImpl;

import java.util.Scanner;

public class Main {

    static void main() {
        ComplaintService service = new ComplaintServiceImpl();
        Scanner sc = new Scanner(System.in);

        // Load dummy data
        service.addComplaint(new Complaint("Sony", "TV issue", "OPEN"));
        service.addComplaint(new Complaint("Ravi", "Network problem", "OPEN"));

        while (true) {
            System.out.println("""
                        1. Add Complaint
                        2. View Complaints
                        3. Update Complaint
                        4. Delete Complaint
                        5. Update via Procedure
                        6. Exit
                    """);

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Desc: ");
                    String desc = sc.nextLine();

                    service.addComplaint(new Complaint(name, desc, "OPEN"));
                }
                case 2 -> service.getAllComplaints()
                        .forEach(c -> System.out.println(c.getId() + " " + c.getName()));

                case 3 -> {
                    try {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Status: ");
                        String status = sc.nextLine();

                        service.updateComplaint(id, status);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        service.deleteComplaint(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Status: ");
                    String status = sc.nextLine();

                    service.updateStatusUsingProcedure(id, status);
                }
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }
}