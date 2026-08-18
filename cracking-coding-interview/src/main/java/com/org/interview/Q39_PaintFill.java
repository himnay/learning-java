package com.org.interview;

// Q8.6 Implement the "paint fill" function: given a screen (2D array of colors),
// a point, and a new color, fill the surrounding area until hitting a border of a different color.
public class Q39_PaintFill {

    enum Color { RED, YELLOW, BLUE, GREEN }

    /** Returns the paint fill. */
    public static boolean paintFill(Color[][] screen, int m, int n, int x, int y, Color newColor, Color oldColor) {
        if (x < 0 || x >= m || y < 0 || y >= n) return false;
        if (screen[x][y] != oldColor) return false;
        screen[x][y] = newColor;
        paintFill(screen, m, n, x - 1, y, newColor, oldColor);
        paintFill(screen, m, n, x + 1, y, newColor, oldColor);
        paintFill(screen, m, n, x, y - 1, newColor, oldColor);
        paintFill(screen, m, n, x, y + 1, newColor, oldColor);
        return true;
    }

    /** Returns the paint fill. */
    public static boolean paintFill(Color[][] screen, int m, int n, int x, int y, Color newColor) {
        if (screen[x][y] == newColor) return false;
        return paintFill(screen, m, n, x, y, newColor, screen[x][y]);
    }

    private static void print(Color[][] screen, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) System.out.print(screen[i][j].ordinal() + " ");
            System.out.println();
        }
    }

    /** Application entry point. */
    public static void main(String[] args) {
        int m = 5, n = 5;
        Color[][] screen = {
            {Color.RED,    Color.RED,    Color.BLUE,   Color.BLUE,   Color.GREEN},
            {Color.RED,    Color.RED,    Color.YELLOW, Color.BLUE,   Color.GREEN},
            {Color.RED,    Color.RED,    Color.YELLOW, Color.BLUE,   Color.GREEN},
            {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.BLUE,   Color.GREEN},
            {Color.GREEN,  Color.GREEN,  Color.GREEN,  Color.GREEN,  Color.GREEN}
        };
        System.out.println("Before:");
        print(screen, m, n);
        paintFill(screen, m, n, 1, 2, Color.GREEN);
        System.out.println("After paintFill at (1,2) with GREEN:");
        print(screen, m, n);
    }
}
