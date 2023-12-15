package repo;

import java.util.ArrayList;

import main.Connection;
import models.Model;

public interface Repository {
	public String[] Insert(String arrayString[], Connection csvScanner);
	public Model FindOne(String col, String[] condition, boolean join, String table, Connection fileScanner);
	public ArrayList<Model> Find(String column, String condition[], boolean sqlJoin, String table, Connection fileScanner);
}
