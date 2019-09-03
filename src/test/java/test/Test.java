package test;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入菱形的行数");
		int y = input.nextInt() / 2 + 1;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < y - i; j++) {
				System.out.print(" ");
			}
			for (int j = 0; j <= 2 * i; j++) {
				if (j == 0 || j == 2 * i) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}

			System.out.println();
		}

		for (int i = 0; i < (y - 1); i++) {
			for (int j = 0; j <= i + 1; j++) {
				System.out.print(" ");
			}
			for (int j = 0; j <= 2 * ((y - 2) - i); j++) {
				if (j == 0 || j == 2 * ((y - 2) - i)) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}