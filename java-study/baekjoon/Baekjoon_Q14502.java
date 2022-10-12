package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Baekjoon_Q14502 {
    public static int N, M, ans;
    public static int[][] lab;
    public static int[][] labCopy;
    public static ArrayList<int[]> zeroes = new ArrayList<>();
    public static ArrayList<int[]> viruses = new ArrayList<>();
    public static boolean[][] isVisited;
    public static boolean[][] isVisited2;
    public static int[] dx = { -1, 1, 0, 0 };
    public static int[] dy = { 0, 0, -1, 1 };
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        lab = new int[N][M];
        labCopy = new int[N][M];
        isVisited = new boolean[N][M];
        isVisited2 = new boolean[N][M];
        ans = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                lab[i][j] = Integer.parseInt(st.nextToken());
                if (lab[i][j] == 0) {
                    int[] temp = { i, j };
                    zeroes.add(temp);
                } else if (lab[i][j] == 2) {
                    int[] temp1 = { i, j };
                    viruses.add(temp1);
                }
            }
        }
        for (int[] innerArr : lab) {
            System.out.println(Arrays.toString(innerArr));
        }
        
        for (int i = 0; i < zeroes.size(); i++) {
            System.out.println(Arrays.toString(zeroes.get(i)));
        }

        combination(0, 0, new int[3]);
        System.out.println(ans);
    }
    
    public static void combination(int depth, int start, int[] out) {
        if (depth == 3) {
            System.out.println(Arrays.toString(out));
            arrayDeepCopy();
            System.out.println("labCopy");
            for (int i = 0; i < out.length; i++) {
                int wallR = zeroes.get(out[i])[0];
                int wallC = zeroes.get(out[i])[1];
                labCopy[wallR][wallC] = 1;
            }
            for (int[] innerArr : labCopy) {
                System.out.println(Arrays.toString(innerArr));
            }
            for (int i = 0; i < viruses.size(); i++) {
                spread(i);
            }
            System.out.println("After Spread");
            for (int[] innerArr : labCopy) {
                System.out.println(Arrays.toString(innerArr));
            }
            countZero();
            return;
        }

        for (int i = start; i < zeroes.size(); i++) {
            if (!isVisited[zeroes.get(i)[0]][zeroes.get(i)[1]]) {
                isVisited[zeroes.get(i)[0]][zeroes.get(i)[1]] = true;
                out[depth] = i;
                combination(depth + 1, i + 1, out);
                isVisited[zeroes.get(i)[0]][zeroes.get(i)[1]] = false;
            }
        }
    }

    public static void arrayDeepCopy() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                labCopy[i][j] = lab[i][j];
            }
        }
    }

    public static void spread(int idx) {
        int[] start = viruses.get(idx);
        Queue<int[]> que = new LinkedList<>();
        que.add(start);
        isVisited2[start[0]][start[1]] = true;

        while (!que.isEmpty()) {
            int[] temp = que.poll();
            for (int i = 0; i < 4; i++) {
                int newR = temp[0] + dx[i];
                int newC = temp[1] + dy[i];
                if (newR >= 0 && newC >= 0 && newR < N && newC < M) {
                    if (!isVisited2[newR][newC] && labCopy[newR][newC] == 0) {
                        labCopy[newR][newC] = 2;
                        isVisited2[newR][newC] = true;
                        int[] temp3 = { newR, newC };
                        que.add(temp3);
                    }
                }
            }
        }
    }

    public static void countZero() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (labCopy[i][j] == 0) {
                    cnt++;
                }
            }
        }

        System.out.println();
        for (int[] innerArr : labCopy) {
            System.out.println(Arrays.toString(innerArr));
        }
        System.out.println(cnt);
        ans = Math.max(ans, cnt);
    }
}
