package ru.sbt.sandbox.barantsev;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

//import java.util.Scanner;

@SpringBootApplication
public class Game {

	public static void main(String[] args) {
		SpringApplication.run(Game.class, args);
//		Scanner in = new Scanner(System.in);
//		System.out.print("ведите количество строк: ");
//		int rows = in.nextInt();
//		System.out.print("введите количество столбцов: ");
//		int cols = in.nextInt();
//		System.out.print("введите количество итераций: ");
//		int n = in.nextInt();
//		GameConsole game = new GameConsole(rows, cols);
//		System.out.println(game.toString());
//		System.out.println("====================");
//		int i = 0;
//		do
//		{
//			if (game.step()) {
//				break;
//			}
//			++i;
//			System.out.println(game.toString());
//			System.out.println("====================");
//		} while (i < n);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}
