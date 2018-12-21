package ru.sbt.sandbox.barantsev;

import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("������ ���������� �����: ");
		int rows = in.nextInt();
		System.out.print("������� ���������� ��������: ");
		int cols = in.nextInt();
		System.out.print("������� ���������� ��������: ");
		int n = in.nextInt();
		GameConsole game = new GameConsole(rows, cols);
		System.out.println(game.toString());
		System.out.println("====================");
		int i = 0;
		do 
		{
			if (game.step()) {
				break;
			}
			++i;
			System.out.println(game.toString());
			System.out.println("====================");
		} while (i < n);

	}

}
