package repo;
import java.io.File;
import java.util.ArrayList;

import main.*;
import models.*;

public class TeamRepository implements Repository {
	private String userPath;

	public TeamRepository() {
		File currentDir = new File("");
		String projectPath = currentDir.getAbsolutePath();
		userPath = projectPath + File.separator + "src" + File.separator + "user.csv";
	}

	public String[] Insert(String[] insertString, Connection fileScanner) {
		int curId = 0;

		while (fileScanner.readFile() != null) {
			curId++;
		}
		String finalTeam = Integer.toString(curId).concat(",").concat(insertString[0]).concat("\n");
		if (fileScanner.writeFile(finalTeam) == true) {
			return insertString;
		}
		return null;
	}

	public ArrayList<Model> Find(String column, String[] condition, boolean joinTable, String table, Connection fileScanner) {
		if (isValid(column, condition, joinTable, table, fileScanner)) {
			System.out.println("Error input conditions unmatched!");
			return null;
		}
		boolean isValid = false;
		ArrayList<Model> Holder = new ArrayList<>();
		String currentRow;
		String[] packageData = null;

		if (column != null) {
			if (joinTable == true) {
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					if ((packageData[0].equals(condition[2])) || (packageData[1].equals(condition[2]))) {
						break;
					}
				}
				Connection fileManager = new Connection(new File(userPath));
				String[] packagerJoin;

				System.out.printf("%-10s | %-35s | %-7s | %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileManager.readFile();
					if (currentRow == null) {
						break;
					}

					packagerJoin = currentRow.split(",");
					if (packagerJoin[2].equals(packageData[0])) {
						isValid = true;
						currentRow = currentRow.concat(",").concat(packageData[1]);
						if (!firstRow) {
							System.out.printf("%-10s | %-35s | %-7s | %-10s\n", packagerJoin[0], packagerJoin[1], packagerJoin[2], packageData[1]);
						} else {
							firstRow = false;
						}
						Holder.add((Model) new User(packagerJoin[0], packagerJoin[1], Integer.parseInt(packagerJoin[2])));
					}
				}
			} else {
				System.out.printf("%-7s | %-35s\n", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					if ((packageData[0].equals(condition[2]) || packageData[1].equals(condition[2])) == true) {
						if (!firstRow) {
							System.out.printf("%-7s | %-35s\n", packageData[0], packageData[1]);
						} else {
							firstRow = false;
						}

						isValid = true;
						if (isValid) {
							Holder.add((Model) new Team(packageData[1], Integer.parseInt(packageData[0])));
						}
					}
				}
			}
		} else {
			if (joinTable) {
				ArrayList<Team> tempTeam = new ArrayList<>();
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					if (isValid) {
						tempTeam.add(new Team(packageData[1], Integer.parseInt(packageData[0])));
					}
					isValid = true;
				}
				Connection fileManager = new Connection(new File(userPath));

				fileManager.readFile();
				System.out.printf("%-10s | %-35s | %-7s | %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileManager.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					isValid = true;
					if (isValid) {
						Holder.add((Model) new User(packageData[0], packageData[1], Integer.parseInt(packageData[2])));
					}
					currentRow = currentRow.concat("|").concat(tempTeam.get(Integer.parseInt(packageData[2]) - 1).team_name);
					if (!firstRow) {
						System.out.printf("%-10s | %-35s | %-7s | %-10s\n", packageData[0], packageData[1], packageData[2], tempTeam.get(Integer.parseInt(packageData[2]) - 1).team_name);
					} else {
						firstRow = false;
					}
				}

			} else {
				System.out.printf("%-7s | %-35s\n", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");
					if (!firstRow) {
						System.out.printf("%-7s | %-35s\n", packageData[0], packageData[1]);
					} else {
						firstRow = false;
					}

					if (isValid) {
						Holder.add((Model) new Team(packageData[1], Integer.parseInt(packageData[0])));
					}
					isValid = true;
				}
			}
		}

		if (isValid) {
			return Holder;
		}
		return null;
	}

	public Model FindOne(String column, String[] condition, boolean joinTable, String table, Connection fileScanner) {
		if (isValid(column, condition, joinTable, table, fileScanner)) {
			System.out.println("Condition unfulfilled!");
			return null;
		}
		boolean isValid = false;
		Model Holder = null;
		String currentRow;
		String[] packageData = null;

		if (column != null) {
			if (joinTable) {
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					if ((packageData[0].equals(condition[2])) || (packageData[1].equals(condition[2]))) {
						break;
					}
				}
				Connection fileManager = new Connection(new File(userPath));
				String[] packagerJoin;

				System.out.printf("%-10s | %-35s | %-7s | %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileManager.readFile();
					if (currentRow == null) {
						break;
					}

					packagerJoin = currentRow.split(",");
					if (packagerJoin[2].equals(packageData[0])) {
						isValid = true;
						currentRow = currentRow.concat(",").concat(packageData[1]);
						if (!firstRow) {
							System.out.printf("%-10s | %-35s | %-7s | %-10s\n", packagerJoin[0], packagerJoin[1], packagerJoin[2], packageData[1]);
						} else {
							firstRow = false;
						}
						Holder = (Model) new User(packagerJoin[0], packagerJoin[1], Integer.parseInt(packagerJoin[2]));
					}
				}
			} else {
				System.out.printf("%-7s | %-35s\n", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					if ((packageData[0].equals(condition[2]) || packageData[1].equals(condition[2])) == true) {
						if (!firstRow) {
							System.out.printf("%-7s | %-35s\n", packageData[0], packageData[1]);
						} else {
							firstRow = false;
						}

						isValid = true;
						if (isValid) {
							Holder = (Model) new Team(packageData[1], Integer.parseInt(packageData[0]));
						}
					}
				}
			}
		} else {
			if (joinTable) {
				ArrayList<Team> tempTeam = new ArrayList<>();
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					if (isValid) {
						tempTeam.add(new Team(packageData[1], Integer.parseInt(packageData[0])));
					}
					isValid = true;
				}
				Connection fileManager = new Connection(new File(userPath));

				fileManager.readFile();
				System.out.printf("%-10s | %-35s | %-7s | %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileManager.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");

					isValid = true;
					if (isValid) {
						Holder = (Model) new User(packageData[0], packageData[1], Integer.parseInt(packageData[2]));
					}
					currentRow = currentRow.concat(",").concat(tempTeam.get(Integer.parseInt(packageData[2]) - 1).team_name);
					if (!firstRow) {
						System.out.printf("%-10s | %-35s | %-7s | %-10s\n", packageData[0], packageData[1], packageData[2], tempTeam.get(Integer.parseInt(packageData[2]) - 1).team_name);
					} else {
						firstRow = false;
					}
				}
			} else {
				System.out.printf("%-7s | %-35s\n", "ID Team", "Team Name");
				boolean firstRow = true;
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packageData = currentRow.split(",");
					if (!firstRow) {
						System.out.printf("%-7s | %-35s\n", packageData[0], packageData[1]);
					} else {
						firstRow = false;
					}

					if (isValid) {
						Holder = (Model) new Team(packageData[1], Integer.parseInt(packageData[0]));
					}
					isValid = true;
				}
			}
		}

		if (isValid) {
			return Holder;
		}
		return null;
	}

	public boolean isValid(String column, String[] condition, boolean joinTable, String table, Connection fileScanner) {
		if ((column != null) && (condition == null)) {
			return true;
		}
		if ((column == null) && (condition != null)) {
			return true;
		}
		if ((joinTable) && (table == null)) {
			return true;
		}
		if ((!joinTable) && (table != null)) {
			return true;
		}
		if (fileScanner == null) {
			return true;
		}
		if ((column != null) && (((column.equals("id")) || (column.equals("Nama"))) == false)) {
			return true;
		}
		if ((column != null) && (table != null) && ((column.length() == 0) || (table.length() == 0))) {
			return true;
		}
		if ((table != null) && (joinTable) && (!(table.equals("User")))) {
			return true;
		}

		return false;
	}
}
