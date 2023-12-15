package models;

public class User extends Model {
	public String NIM, userName;
	public User(String NIM, String userName, int team_id) {
		super(team_id);
		this.NIM = NIM;
		this.userName = userName;
	}
}
