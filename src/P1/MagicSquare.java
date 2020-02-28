package P1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MagicSquare {

    public static boolean isLegalMagicSquare(String fileName) throws IOException {
        int column, row;//用来记录行数和列数
        int sum = 0, temp;
        boolean result = false;//记录结果
        String eachrow;
        //准备工作：计算行列数，总和
        File file = new File(fileName);
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bf = new BufferedReader(inputReader);

        ArrayList<String> rowlist = new ArrayList<String>();
        //复制到容器中
        while ((eachrow = bf.readLine()) != null) {
            rowlist.add(eachrow);//一行一行读进来
        }
        String[] eachline = (rowlist.get(0)).split("\t");//依照分隔符分割第一行
        column = eachline.length;//根据第一行计算出方阵的行列数,用于接下来对幻方的判断
        //判断是否为幻方
        //行列数是否相等
        row = rowlist.size();
        if (column != row) {
            System.out.println("行列数不相等！");
            return result;
        }
        //建立二维数组
        int[][] MS = new int[row][column];//创立一个二维数组
        for (int i = 0; i < column; i++) {
            try {
                MS[0][i] = Integer.valueOf(eachline[i]);//把第一行放进去
            } catch (Exception e) {
                System.out.println("矩阵中存在非正整数的元素！");
                return result;
            }
            if (MS[0][i] < 0) {
                System.out.println("矩阵中存在非正整数的元素！");
                return result;
            }
            sum += MS[0][i];
        }
        for (int i = 1; i < row; i++) {
            eachline = (rowlist.get(i)).split("\t");//每一行都分割
            if (eachline.length != column) {
                System.out.println("并非矩阵！");
                return result;
            }
            for (int j = 0; j < column; j++) {
                try {
                    MS[i][j] = Integer.valueOf(eachline[j]);//每一行都放入二维数组中
                } catch (Exception e) {
                    System.out.println("矩阵中存在非正整数的元素！");
                    return result;
                }
            }
        }
        //每行和是否相等
        for (int i = 1; i < row; i++) {
            temp = 0;
            for (int j = 0; j < column; j++) {
                temp += MS[i][j];
            }
            if (temp != sum) {
                System.out.println("每行和不相等！");
                return result;
            }
        }
        //每列和是否相等
        for (int j = 0; j < column; j++) {
            temp = 0;
            for (int i = 0; i < row; i++) {
                temp += MS[i][j];
            }
            if (temp != sum) {
                System.out.println("每列和不相等！");
                return result;
            }
        }
        temp = 0;
        //斜对角线和是否相等
        for (int i = 0; i < row; i++) {
            temp += MS[i][i];
        }
        if (temp != sum) {
            System.out.println("对角线和不相等！");
            return result;
        }
        temp = 0;
        for (int i = 0; i < row; i++) {
            temp += MS[i][row - i - 1];
        }
        if (temp != sum) {
            System.out.println("对角线和不相等！");
            return result;
        }
        result = true;
        bf.close();
        inputReader.close();
        return result;

    }

    public static boolean generateMagicSquare(int n, String filename) throws IOException {

        //若n输入不合法 需要给出提示并强行退出
        //若n为偶数或者是负数 即是不合法
        if (n % 2 == 0 || n < 0) {
            System.out.println("False:Invalid value n!");
            System.exit(0);
        }

        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        File file = new File(filename);
        OutputStream os = new FileOutputStream(file);
        PrintWriter pw = new PrintWriter(os);

        //大循环是依次将n*n-1放进二维数组中
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            //以下的一系列if-else语句，均是为了防止数组下表出界而设定的
            //这句话相当于是 将n*n个数分成n个组，每组n个数且数字大小连续 确保每一组的相对小的数要和上一组相对大的数放同一行
            if (i % n == 0)
                row++;
            else {
                //行下标不断减小 至0时又回到最大行下标
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                //列下标不断增加 至最大行下标又回到0
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        //下面这个循环用来输出构建好的幻方
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print(magic[i][j] + "\t");
                pw.print(magic[i][j] + "\t");
            }
            System.out.println();
            pw.println();
        }
        pw.close();
        os.close();5
        return true;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(isLegalMagicSquare("D://IdeaProjects/Lab1/lab1/src/P1/txt/1.txt"));
        System.out.println();
        System.out.println(isLegalMagicSquare("D://IdeaProjects/Lab1/lab1/src/P1/txt/2.txt"));
        System.out.println();
        System.out.println(isLegalMagicSquare("D://IdeaProjects/Lab1/lab1/src/P1/txt/3.txt"));
        System.out.println();
        System.out.println(isLegalMagicSquare("D://IdeaProjects/Lab1/lab1/src/P1/txt/4.txt"));
        System.out.println();
        System.out.println(isLegalMagicSquare("D://IdeaProjects/Lab1/lab1/src/P1/txt/5.txt"));
        System.out.println();
        System.out.println("请输入幻方阶n的值：");
        Scanner in = new Scanner(System.in);
        generateMagicSquare(in.nextInt(), "D://IdeaProjects/Lab1/lab1/src/P1/txt/6.txt");
        System.out.println("判断是否为幻方：");
        System.out.println(isLegalMagicSquare("D://IdeaProjects/Lab1/lab1/src/P1/txt/6.txt"));
    }
}

