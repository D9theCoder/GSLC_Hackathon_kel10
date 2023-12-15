package repo;

import java.io.*;
import java.util.*;

import main.Connection;
import models.*;

public class UserRepository implements Repository {
	private String teamPath;
	
	public UserRepository() {
		teamPath = System.getProperty("user.dir").replace("\\", "\\\\").concat("\\\\src\\\\teams.csv");
	}
	
	public String[] Insert(String[] insertString, Connection fileScanner) {
		Connection file = new Connection(new File(teamPath));
		int member = 0, currentTeam = -1;
		String currentRow;
		String[] splitRow = null;
		
		while (true) {
			currentRow = file.readFile();
			if (currentRow == null) {
				break;
			}
			splitRow = currentRow.split(",");
			if (splitRow[1].equals(insertString[2])) {
				currentTeam = Integer.parseInt(splitRow[0]);
				break;
			}
		}
		
		if (currentTeam == -1) {
			currentTeam = Integer.parseInt(splitRow[0]) + 1;
			currentRow = Integer.toString(Integer.parseInt(splitRow[0]) + 1).concat(",").concat(insertString[2]).concat("\n");
			file.writeFile(currentRow);
		}
		
		fileScanner.readFile();
		while (true) {
			currentRow = fileScanner.readFile();
			if (currentRow == null) {
				break;
			}
			splitRow = currentRow.split(",");
			
			if (Integer.parseInt(splitRow[2]) == currentTeam) {
				member++;
			}
		}
		
		if (member == 3) {
			System.out.println("Error! team full");
		} else {
			currentRow = insertString[0].concat(",").concat(insertString[1]).concat(",").concat(Integer.toString(currentTeam)).concat("\n");
			fileScanner.writeFile(currentRow);
			insertString[2] = Integer.toString(currentTeam);
			return insertString;
		}
		
		return null;
	}
	
	public ArrayList<Model> Find(String col, String[] condition, boolean joinTable, String table, Connection fileScanner) {
		if (isValid(col, condition, joinTable, table, fileScanner)) {
			System.out.println("Error input conditions unmatched!");
			return null;
		}
		
		boolean isData = false;
		ArrayList<Model> holder = new ArrayList<>();
		String currentRow;
		String[] packager = null;
		
		if (col != null) {
			if (joinTable) {
				Connection file = new Connection(new File(teamPath));
				ArrayList<Team> tempTeam = new ArrayList<>();
				while (true) {
					currentRow = file.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if (isData) {
						tempTeam.add(new Team(packager[1], Integer.parseInt(packager[0])));
					}
					isData = true;
				}
				
				System.out.printf("%-10s %-10s %-10s %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if ((packager[0].equals(condition[2])) || (packager[1].equals(condition[2])) || (packager[2].equals(condition[2]))) {
						isData = true;
						currentRow = String.format("%-10s %-10s %-10s %-10s", packager[0], packager[1], packager[2], tempTeam.get(Integer.parseInt(packager[2]) - 1).team_name);
						holder.add((Model) new User(packager[0], packager[1], Integer.parseInt(packager[2])));
						System.out.println(currentRow);
					}
				}
			} else {
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if ((condition[2].equals(packager[0])) || (condition[2].equals(packager[1])) || (condition[2].equals(packager[2]))) {
						System.out.println(currentRow);
						
						isData = true;
						if (isData) {
							holder.add((Model) new User(packager[0], packager[1], Integer.parseInt(packager[2])));
						}
					}
				}
			}
		} else {
			if (joinTable) {
				Connection file = new Connection(new File(teamPath));
				ArrayList<Team> tempTeam = new ArrayList<>();
				
				while (true) {
					currentRow = file.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if (isData) {
						tempTeam.add(new Team(packager[1], Integer.parseInt(packager[0])));
					}
					isData = true;
				}
				
				fileScanner.readFile();
				System.out.printf("%-15s %-15s %-10s %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");

					isData = true;
					if (isData) {
						holder.add((Model) new User(packager[0], packager[1], Integer.parseInt(packager[2])));
					}
					currentRow = String.format("%-15s %-15s %-10s %-10s", packager[0], packager[1], packager[2], tempTeam.get(Integer.parseInt(packager[2]) - 1).team_name);
					System.out.println(currentRow);
				}
			} else {
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					System.out.println(currentRow);
					
					if (isData) {
						holder.add((Model) new User(packager[0], packager[1], Integer.parseInt(packager[2])));
					}
					isData = true;
				}
			}
		}
		if (isData) {
			return holder;
		}
		return null;
	}

	public Model FindOne(String col, String[] condition, boolean joinTable, String table, Connection fileScanner) {

		boolean isData = false;
		Model holder = null;
		String currentRow;
		String[] packager = null;
		
		if (col != null) {
			if (joinTable) {
				Connection file = new Connection(new File(teamPath));
				ArrayList<Team> tempTeam = new ArrayList<>();
				while (true) {
					currentRow = file.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if (isData) {
						tempTeam.add(new Team(packager[1], Integer.parseInt(packager[0])));
					}
					isData = true;
				}
				
				System.out.printf("%-10s %-10s %-10s %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if ((packager[0].equals(condition[2])) || (packager[1].equals(condition[2])) || (packager[2].equals(condition[2]))) {
						isData = true;
						currentRow = String.format("%-10s %-10s %-10s %-10s", packager[0], packager[1], packager[2], tempTeam.get(Integer.parseInt(packager[2]) - 1).team_name);
						holder = (Model) new User(packager[0], packager[1], Integer.parseInt(packager[2]));
						System.out.println(currentRow);
					}
				}
			} else {
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if ((condition[2].equals(packager[0])) || (condition[2].equals(packager[1])) || (condition[2].equals(packager[2]))) {
						System.out.println(currentRow);
						
						isData = true;
						if (isData) {
							holder = (Model) new User(packager[0], packager[1], Integer.parseInt(packager[2]));
						}
					}
				}
			}
		} else {
			if (joinTable) {
				Connection file = new Connection(new File(teamPath));
				ArrayList<Team> tempTeam = new ArrayList<>();
				
				while (true) {
					currentRow = file.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					if (isData) {
						tempTeam.add(new Team(packager[1], Integer.parseInt(packager[0])));
					}
					isData = true;
				}
				
				fileScanner.readFile();
				System.out.printf("%-10s %-10s %-10s %-10s\n", "NIM", "Name", "ID Team", "Team Name");
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					
					isData = true;
					if (isData) {
						holder = (Model) new User(packager[0], packager[1], Integer.parseInt(packager[2]));
					}
					currentRow = String.format("%-10s %-10s %-10s %-10s", packager[0], packager[1], packager[2], tempTeam.get(Integer.parseInt(packager[2]) - 1).team_name);
					System.out.println(currentRow);
				}
			} else {
				while (true) {
					currentRow = fileScanner.readFile();
					if (currentRow == null) {
						break;
					}
					packager = currentRow.split(",");
					System.out.println(currentRow);
					
					if (isData) {
						holder = (Model) new User(packager[0], packager[1], Integer.parseInt(packager[2]));
					}
					isData = true;
				}
			}
		}
		if (isData) {
			return holder;
		}
		return null;
	}
	
	public boolean isValid(String col, String[] condition, boolean joinTable, String table, Connection fileScanner) {
		if ((col != null) && (condition == null)) {
			return true;
		}
		if ((col == null) && (condition != null)) {
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
		if ((col != null) && (((col.equals("NIM")) || (col.equals("Name")) || (col.equals("ID Team"))) == false)) {
			return true;
		}
		if ((col != null) && (table != null) && ((col.length() == 0) || (table.length() == 0))) {
			return true;
		}
		if ((table != null) && (joinTable) && (!(table.equals("Team")))) {
			return true;
		}
		
		return false;
	}
}
