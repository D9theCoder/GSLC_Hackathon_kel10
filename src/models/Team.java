package models;

public class Team extends Model {
	public String team_name;
	public Team(String team_name, int team_id) {
		super(team_id);
		this.team_name = team_name;
	}
}
