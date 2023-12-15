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

		if (subCon == 1) {
			Connection fileManager = new Connection(new File(userPath));
			UserRepository userInserter = new UserRepository();
			String[] User = new String[3];

			System.out.print("add nim: ");
			User[0] = scanner.nextLine();
			System.out.print("add name: ");
			User[1] = scanner.nextLine();
			System.out.print("add team: ");
			User[2] = scanner.nextLine();
			userInserter.Insert(User, fileManager);
		} else if (subCon == 2) {
			Connection fileManager = new Connection(new File(teamPath));
			TeamRepository teamInserter = new TeamRepository();
			String[] Team = new String[1];

			System.out.print("add team: ");
			Team[0] = scanner.nextLine();
			teamInserter.Insert(Team, fileManager);
		}
	}

	public void showData() {
		System.out.print("Which table to show? 1. User, 2. Team.\n ");
		int subCon = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Want to filter by choice? 1. yes, 2. no\nChoose: ");
		int subCon2 = scanner.nextInt();
		scanner.nextLine();

		if (subCon == 1) {
			if (subCon2 == 1) {
				System.out.print(
						"Please add filter [column;=;choice (column = NIM/Name/Team)]: ");
				String colState = scanner.nextLine();
				String[] Packager = colState.split(";");
				System.out.print("Join table? [y/n]: ");
				String joinState = scanner.nextLine();

				UserRepository userChoice = new UserRepository();
				Connection fileManager = new Connection(new File(userPath));

				if (joinState.equals("y")) {
					userChoice.Find(Packager[0], Packager, true, "Team", fileManager);
					System.out.println("\n");
				} else if (joinState.equals("n")) {
					userChoice.Find(Packager[0], Packager, false, null, fileManager);
					System.out.println("\n");
				}
			} else {
				System.out.print("Would you like join table with User[y/n (Case Sensitive)]: ");
				String joinState = scanner.nextLine();
				UserRepository UserShowUnchoiceal = new UserRepository();
				Connection fileManager = new Connection(new File(userPath));

				if (joinState.equals("y")) {
					UserShowUnchoiceal.Find(null, null, true, "Team", fileManager);
					System.out.println("\n");
				} else if (joinState.equals("n")) {
					UserShowUnchoiceal.Find(null, null, false, null, fileManager);
					System.out.println("\n");
				}
			}
		} else if (subCon == 2) {
			if (subCon2 == 1) {
				System.out.print(
						"Please add filter [column;=;choice (column = id or Nama)]: ");
				String colState = scanner.nextLine();
				String[] Packager = colState.split(";");
				System.out.print("Join table? [y/n]: ");
				String joinState = scanner.nextLine();

				TeamRepository TeamShowchoiceal = new TeamRepository();
				Connection fileManager = new Connection(new File(teamPath));

				if (joinState.equals("y")) {
					TeamShowchoiceal.Find(Packager[0], Packager, true, "User", fileManager);
					System.out.println("\n");
				} else if (joinState.equals("n")) {
					TeamShowchoiceal.Find(Packager[0], Packager, false, null, fileManager);
					System.out.println("\n");
				}

			} else {
				System.out.print("Join table? [y/n]: ");
				String joinState = scanner.nextLine();
				TeamRepository TeamShowUnchoiceal = new TeamRepository();
				Connection fileManager = new Connection(new File(teamPath));

				if (joinState.equals("y")) {
					TeamShowUnchoiceal.Find(null, null, true, "User", fileManager);
					System.out.println("\n");
				} else if (joinState.equals("n")) {
					TeamShowUnchoiceal.Find(null, null, false, null, fileManager);
					System.out.println("\n");
				}
			}
		}
	}
}
