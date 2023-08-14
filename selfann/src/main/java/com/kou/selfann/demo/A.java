package com.kou.selfann.demo;

/**
 * @author JIAJUN KOU
 */

public class A {

    private final int[][] map;

    public A(int[][] map) {
        this.map = map;
    }

    public static void main(String[] args) {
        int[][] map = new int[][]{
                {2, 4, 6, 8, 7, 9},
                {3, 8, 9, 1, 4, 5},
                {6, 9, 12, 2, 15, 11},
                {8, 7, 18, 7, 13, 2},
                {7, 2, 10, 4, 3, 7},
                {9, 5, 6, 17, 5, 1}
        };

        A c = new A(map);
        boolean[] result = c.computeFastestPath(map);
        Integer integer = c.printPath(result);
        System.out.println("***********");
        System.out.println(integer);

    }

    public boolean[] computeFastestPath(int[][] map) {
        int pathLength = map.length + map[0].length - 2;
        boolean[] result = new boolean[pathLength];

        int[][] mapOfMinimalSums = buildMapOfMinimalSums();

        int x = map.length - 1;
        int y = map[0].length - 1;

        for (int i = pathLength - 1; i >= 0; i--) {
            if (x == 0)
                result[i] = true;
            else if (y == 0)
                result[i] = false;
            else if (mapOfMinimalSums[x][y] == map[x][y] + mapOfMinimalSums[x][y - 1]) {
                result[i] = true;
                y--;
            } else {
                result[i] = false;
                x--;
            }
        }

        return result;
    }

    public Integer printPath(boolean[] result) {
        String[][] newPath = new String[map.length][map[0].length];
        int x = 0;
        int y = 0;
        newPath[x][y] = String.valueOf(map[x][y]);

        for (boolean b : result) {
            if (b) {
                y++;
            } else {
                x++;
            }
            newPath[x][y] = String.valueOf(map[x][y]);
        }

        int sum = 0;
        for (int i = 0 ; i < map.length; i++) {
            for (int j = 0 ; j < map[0].length; j++) {
                if (newPath[i][j] == null) {
                    System.out.print(" , ");
                } else {
                    System.out.print(newPath[i][j] + ", ");
                    sum = sum+ Integer.parseInt(newPath[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
        return sum;
    }


    private int[][] buildMapOfMinimalSums() {
        int[][] mapOfSums = new int[map.length][map[0].length];
        for (int i = 0 ; i < map.length; i++) {
            for (int j = 0 ; j < map[0].length; j++) {
                if (i == 0 && j == 0)
                    mapOfSums[i][j] = map[i][j];
                else if (i == 0) {
                    mapOfSums[i][j] = mapOfSums[i][j - 1] + map[i][j];
                }
                else if (j == 0)
                    mapOfSums[i][j] = mapOfSums[i-1][j] + map[i][j];
                else
                    mapOfSums[i][j] = Math.min(mapOfSums[i-1][j], mapOfSums[i][j-1]) + map[i][j];
            }
        }
        return mapOfSums;
    }

}