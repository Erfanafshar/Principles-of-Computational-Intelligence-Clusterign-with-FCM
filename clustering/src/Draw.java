import java.awt.*;
import java.awt.event.*;

public class Draw extends Frame {

    private int[] x;
    private int[] y;
    private int[] x2;
    private int[] y2;
    private int num;
    private int num2;
    private Color[] col2;
    private Color[] col3;
    private Color[] col4;


    Draw(double[][] points, double[][] U, double[][] C, int number, int number2) {
        super("Clustering");
        setSize(1500, 1000);
        setLocation(200, 0);
        setVisible(true);

        num = number;
        x = new int[num];
        y = new int[num];

        num2 = number2;
        x2 = new int[num2];
        y2 = new int[num2];

        col2 = new Color[num];
        col3 = new Color[num];
        col4 = new Color[num];

        for (int i = 0; i < num; i++) {
            x[i] = (int) (points[i][0] * 1000 + 50);
            y[i] = (int) (points[i][1] * 1000 + 100);
        }

        for (int i = 0; i < num2; i++) {
            x2[i] = (int) (C[i][0] * 1000 + 50);
            y2[i] = (int) (C[i][1] * 1000 + 100);
        }

        if (num2 == 2) {
            for (int i = 0; i < num; i++) {
                if (U[i][0] > U[i][1])
                    col2[i] = Color.red;
                else
                    col2[i] = Color.blue;
            }
        }

        if (num2 == 3) {
            for (int i = 0; i < num; i++) {
                double max = -1.0;
                int maxIndex = -1;
                for (int j = 0; j < num2; j++) {
                    if (U[i][j] > max) {
                        max = U[i][j];
                        maxIndex = j;
                    }
                }
                col3[i] = getCol3(maxIndex);
            }
        }


        if (num2 == 4) {
            for (int i = 0; i < num; i++) {
                double max = -1.0;
                int maxIndex = -1;
                for (int j = 0; j < num2; j++) {
                    if (U[i][j] > max) {
                        max = U[i][j];
                        maxIndex = j;
                    }
                }
                col4[i] = getCol4(maxIndex);
            }
        }

        addWindowListener(new WindowAdapter() {
                              public void windowClosing(WindowEvent e) {
                                  dispose();
                                  System.exit(0);
                              }
                          }
        );
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (num2 == 2) {
            for (int i = 0; i < num; i++) {
                g2d.setColor(col2[i]);
                g2d.drawRoundRect(x[i], y[i], 5, 5, 2, 2);
            }
            for (int i = 0; i < num2; i++) {
                g2d.setColor(getCol2(i));
                g2d.drawRoundRect(x2[i], y2[i], 20, 20, 8, 8);
            }
        }
        if (num2 == 3) {
            for (int i = 0; i < num; i++) {
                g2d.setColor(col3[i]);
                g2d.drawRoundRect(x[i], y[i], 5, 5, 2, 2);
            }
            for (int i = 0; i < num2; i++) {
                g2d.setColor(getCol3(i));
                g2d.drawRoundRect(x2[i], y2[i], 20, 20, 8, 8);
            }
        }
        if (num2 == 4) {
            for (int i = 0; i < num; i++) {
                g2d.setColor(col4[i]);
                g2d.drawRoundRect(x[i], y[i], 5, 5, 2, 2);
            }
            for (int i = 0; i < num2; i++) {
                g2d.setColor(getCol4(i));
                g2d.drawRoundRect(x2[i], y2[i], 20, 20, 8, 8);
            }
        }

    }

    private Color getCol2(int num) {
        if (num == 0)
            return Color.red;
        return Color.blue;
    }

    private Color getCol3(int num) {
        switch (num) {
            case 0:
                return Color.red;
            case 1:
                return Color.blue;
            case 2:
                return Color.yellow;
        }
        return Color.black;
    }

    private Color getCol4(int num) {
        switch (num) {
            case 0:
                return Color.red;
            case 1:
                return Color.blue;
            case 2:
                return Color.yellow;
            case 3:
                return Color.gray;
        }
        return Color.black;
    }
}

