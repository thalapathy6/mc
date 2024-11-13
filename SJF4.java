import java.util.Scanner;

public class SJF {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("*** Shortest Job First Scheduling (Preemptive) ***");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] remainingTime = new int[n];
        int[] completionTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time for Process " + (i + 1) + ": ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();
            remainingTime[i] = burstTime[i]; // Initially, remaining time = burst time
        }

        int completed = 0, currentTime = 0, shortest = -1;
        boolean found;

        // Process each time unit until all processes are completed
        while (completed < n) {
            int minTime = Integer.MAX_VALUE;
            found = false;

            // Find process with shortest remaining time
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && remainingTime[i] < minTime && remainingTime[i] > 0) {
                    minTime = remainingTime[i];
                    shortest = i;
                    found = true;
                }
            }

            if (!found) { // If no process is available, move time forward
                currentTime++;
                continue;
            }

            // Execute the found process for 1 time unit
            remainingTime[shortest]--;
            currentTime++;

            // If process is fully executed
            if (remainingTime[shortest] == 0) {
                completionTime[shortest] = currentTime;
                turnaroundTime[shortest] = completionTime[shortest] - arrivalTime[shortest];
                waitingTime[shortest] = turnaroundTime[shortest] - burstTime[shortest];
                completed++;
            }
        }

        // Calculate and display results
        System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        float totalTAT = 0, totalWT = 0;
        for (int i = 0; i < n; i++) {
            totalTAT += turnaroundTime[i];
            totalWT += waitingTime[i];
            System.out.println("P" + (i + 1) + "\t" + arrivalTime[i] + "\t" + burstTime[i] + "\t" + completionTime[i]
                    + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }

        System.out.println("\nAverage Turnaround Time: " + (totalTAT / n));
        System.out.println("Average Waiting Time: " + (totalWT / n));

        sc.close();
    }
}
