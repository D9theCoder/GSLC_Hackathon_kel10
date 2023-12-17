package main;

import java.io.File;
import java.util.Scanner;

import repo.*;

public class Main {
	public static Scanner scanner = new Scanner(System.in);

	public static String userPath = new File("src/user.csv").getAbsolutePath();
	public static String teamPath = new File("src/teams.csv").getAbsolutePath();

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		menu();
	}

	public void menu() {
		while (true) {
			System.out.println("Welcome to Hackathon team management");
			System.out.println("Choose an option:");
			System.out.println("1. Return to main menu");
			System.out.println("2. Insert Data");
			System.out.println("3. Show");
			System.out.println("4. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			if (choice == 1) {
				menu();
			} else if (choice == 2) {
				insertData();
			} else if (choice == 3) {
				showData();
			} else if (choice == 4) {
				break;
			}
		}
		scanner.close();
	}

	public void insertData() {
		System.out.println("Which table to insert? 1. User 2. Team");
		int subCon = scanner.nextInt();
		scanner.nextLine();

		Connection fileManager;
		Repository repository;
		String[] data;

		if (subCon == 1) {
			fileManager = new Connection(new File(userPath));
			repository = new UserRepository();
			data = new String[3];

			System.out.print("add nim: ");
			data[0] = scanner.nextLine();
			System.out.print("add name: ");
			data[1] = scanner.nextLine();
			System.out.print("add team: ");
			data[2] = scanner.nextLine();
		} else if (subCon == 2) {
			fileManager = new Connection(new File(teamPath));
			repository = new TeamRepository();
			data = new String[1];

			System.out.print("add team: ");
			data[0] = scanner.nextLine();
		} else {
			System.out.println("Invalid option. Please try again.");
			return;
		}

		repository.Insert(data, fileManager);
	}

	public void showData() {
		System.out.println("Which table to show? 1. User, 2. Team.");
		int subCon = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Want to filter by choice? 1. Yes, 2. No");
		int subCon2 = scanner.nextInt();
		scanner.nextLine();

		Repository repository;
		Connection fileManager;
		String[] packager;

		if (subCon == 1) {
			repository = new UserRepository();
			fileManager = new Connection(new File(userPath));
		} else if (subCon == 2) {
			repository = new TeamRepository();
			fileManager = new Connection(new File(teamPath));
		} else {
			System.out.println("Invalid option. Please try again.");
			return;
		}

		if (subCon2 == 1) {
			System.out.print("Please add filter [column;=;choice (column = NIM/Name/Team)]: ");
			String colState = scanner.nextLine();
			packager = colState.split(";");
			System.out.print("Join table? [y/n]: ");
			String joinState = scanner.nextLine();

			if (joinState.equals("y")) {
				repository.Find(packager[0], packager, true, (subCon == 1) ? "Team" : "User", fileManager);
			} else if (joinState.equals("n")) {
				repository.Find(packager[0], packager, false, null, fileManager);
			}
		} else if (subCon2 == 2) {
			System.out.print("Join table? [y/n]: ");
			String joinState = scanner.nextLine();

			if (joinState.equals("y")) {
				repository.Find(null, null, true, (subCon == 1) ? "Team" : "User", fileManager);
			} else if (joinState.equals("n")) {
				repository.Find(null, null, false, null, fileManager);
			}
		} else {
			System.out.println("Invalid option. Please try again.");
		}
	}
}
