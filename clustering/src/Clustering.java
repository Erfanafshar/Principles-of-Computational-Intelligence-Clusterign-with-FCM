import java.io.File;
import java.util.Random;
import java.util.Scanner;

class Clustering {

    private int NUMBER_OF_DATA;
    private int DIMENSION_OF_DATA;
    private int NUMBER_OF_CLUSTERS = 3;
    private double MC = 2.0;
    private double MU = 2.0;
    private double Mcost = 2.0;
    private double epsilon = Math.pow(10, -10);
    private double maxCost = 12.5;
    private String dataFileName = "sample3.csv";
    private Scanner scanner1;
    private Scanner scanner2;
    private double[][] points;
    private double[][] U;
    private double[][] U2;
    private double[][] C;
    private double cost;

    Clustering() {
        getInput();
        countDataDimension(countDataNumber());
        setArrays();
        setInput();

        initializeMemberships();
        while (true) {
            calculateCenterOfClusters();
            calculateMemberships();
            calculateCost();
            System.out.println(cost);
            if (checkEndCondition()) {
                setU();
                break;
            }
            setU();
        }
        drawResult();
        System.out.println("moz");
    }

    private void getInput() {
        try {
            File file = new File(dataFileName);
            scanner1 = new Scanner(file);
            scanner2 = new Scanner(file);
        } catch (Exception e) {
            System.out.println("file not found");
        }
    }

    private String countDataNumber() {
        int cnt;
        String dataLine = null;
        for (cnt = 0; scanner1.hasNextLine(); cnt++) {
            if (cnt == 0)
                dataLine = scanner1.nextLine();
            else
                scanner1.nextLine();
        }
        NUMBER_OF_DATA = cnt - 1;
        return dataLine;
    }

    private void countDataDimension(String dataLine) {
        DIMENSION_OF_DATA = dataLine.split(",").length;
    }

    private void setArrays() {
        points = new double[NUMBER_OF_DATA][DIMENSION_OF_DATA];
        U = new double[NUMBER_OF_DATA][NUMBER_OF_CLUSTERS];
        U2 = new double[NUMBER_OF_DATA][NUMBER_OF_CLUSTERS];
        C = new double[NUMBER_OF_CLUSTERS][DIMENSION_OF_DATA];
    }

    private void setInput() {
        scanner2.nextLine();
        String str[];
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            str = scanner2.nextLine().split(",");
            for (int j = 0; j < DIMENSION_OF_DATA; j++) {
                points[i][j] = Double.valueOf(str[j]);
            }
        }
    }

    private void initializeMemberships() {
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            for (int j = 0; j < NUMBER_OF_CLUSTERS; j++) {
                U[i][j] = random.nextDouble();
            }
        }
    }

    private void calculateCenterOfClusters() {
        for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
            for (int j = 0; j < DIMENSION_OF_DATA; j++) {
                double numerator = 0.0;
                double denominator = 0.0;
                for (int k = 0; k < NUMBER_OF_DATA; k++) {
                    numerator += Math.pow(U[k][i], MC) * points[k][j];
                }
                for (int k = 0; k < NUMBER_OF_DATA; k++) {
                    denominator += Math.pow(U[k][i], MC);
                }
                C[i][j] = numerator / denominator;
            }
        }
    }

    private void calculateMemberships() {
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            for (int j = 0; j < NUMBER_OF_CLUSTERS; j++) {
                double numerator1 = 1.0;
                double denominator1 = 0.0;

                double sum = 0.0;
                for (int k = 0; k < DIMENSION_OF_DATA; k++) {
                    sum += Math.pow(points[i][k] - C[j][k], 2.0);
                }
                double numerator2;
                double denominator2;
                numerator2 = Math.sqrt(sum);

                for (int k = 0; k < NUMBER_OF_CLUSTERS; k++) {
                    sum = 0.0;
                    for (int l = 0; l < DIMENSION_OF_DATA; l++) {
                        sum += Math.pow(points[i][l] - C[k][l], 2.0);
                    }
                    denominator2 = Math.sqrt(sum);
                    denominator1 += Math.pow(numerator2 / denominator2, 2.0 / (MU - 1));
                }

                U2[i][j] = numerator1 / denominator1;
            }
        }
    }

    private boolean checkEndCondition() {
        double sum = 0.0;
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            for (int j = 0; j < NUMBER_OF_CLUSTERS; j++) {
                sum += Math.pow(Math.abs(U2[i][j] - U[i][j]), 2.0);
            }
        }
        sum = Math.sqrt(sum);
        return sum < epsilon;
    }

    private void setU() {
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            for (int j = 0; j < NUMBER_OF_CLUSTERS; j++) {
                U[i][j] = U2[i][j];
            }
        }
    }

    private void drawResult() {
        new Draw(points, U, C, NUMBER_OF_DATA, NUMBER_OF_CLUSTERS);
    }

    private void calculateCost() {
        double sum = 0.0;
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            for (int j = 0; j < NUMBER_OF_CLUSTERS; j++) {
                double sum2 = 0.0;
                for (int k = 0; k < DIMENSION_OF_DATA; k++) {
                    sum2 += Math.pow(points[i][k] - C[j][k], 2);
                }
                sum2 = Math.sqrt(sum2);
                sum += Math.pow(U[i][j], Mcost) * Math.pow(sum2, 2);
            }
        }
        cost = sum;
    }

    private boolean checkCost() {
        return cost < maxCost;
    }
}
