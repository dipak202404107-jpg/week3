
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMarksRecord {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> marks = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Number of students must be positive.");
            sc.close();
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.print("Enter marks of student "
                    + (i + 1) + ": ");
            int mark = sc.nextInt();

            if (mark < 0 || mark > 100) {
                System.out.println("Invalid marks! Enter 0 to 100.");
                i--;
            } else {
                marks.add(mark);
            }
        }

        int highest = marks.get(0);
        int lowest = marks.get(0);

        for (int mark : marks) {
            if (mark > highest) {
                highest = mark;
            }

            if (mark < lowest) {
                lowest = mark;
            }
        }

        System.out.println("\nAll Marks: " + marks);
        System.out.println("Highest Marks: " + highest);
        System.out.println("Lowest Marks: " + lowest);

        sc.close();
    }
}
