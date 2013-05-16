import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class BaseballElimination {
	private final Map<String, Integer> teams = new HashMap<String, Integer>();
	private final Map<Integer, String> int2teams = new HashMap<Integer, String>();
	private final Map<String, Tuple<Boolean, Iterable<String>>> certificates = 
									new HashMap<String, Tuple<Boolean, Iterable<String>>>();
	private final int[] 	wins;
	private final int[] 	loses;
	private final int[] 	remaining;
	private final int[][]	games;
	private int numOfPairsWithNonZerogames;
	
	public BaseballElimination(String filename){
		In in = new In(filename);
		int numOfTeams = in.readInt();
		this.wins = new int[numOfTeams];
		this.loses = new int[numOfTeams];
		this.remaining = new int[numOfTeams];
		this.games = new int[numOfTeams][numOfTeams];
		int count = 0;
		
		while(count < numOfTeams){
			String teamName = in.readString();
			teams.put(teamName, count);
			int2teams.put(count, teamName);
				
			wins[count] = in.readInt();
			loses[count] = in.readInt();
			remaining[count] = in.readInt();
			for(int i=0;i<numOfTeams;i++){
				int gamesLeft = in.readInt();
				games[count][i] = gamesLeft;
				if(gamesLeft != 0){
					numOfPairsWithNonZerogames++;
				}
			}
			count++;
		}
	}
	
	public int numberOfTeams(){
		return teams.size();
	}
	
	public Iterable<String> teams(){
		return teams.keySet();
	}
	
	public int wins(String team){
		validate(team);
		return wins[teams.get(team)];
	}

	
	public  int losses(String team){
		validate(team);
		return loses[teams.get(team)];
	}
	
	public int remaining(String team){
		validate(team);
		return remaining[teams.get(team)];
	}
	
	public int against(String team1, String team2){
		validate(team1);
		validate(team2);
		return games[teams.get(team1)][teams.get(team2)];
	}
	
	public boolean isEliminated(String team){
		return certificateOfElimination(team) != null;
	}
	
	public Iterable<String> certificateOfElimination(String team){
		validate(team);
		
		Tuple<Boolean, Iterable<String>> tuple = certificates.get(team);
		if(tuple != null){
			return tuple.getJ();
		}
		
		Integer s = teams.get(team);
		
		if(doTrivial(s)){
			add4Trivial(s);
		}else{
			computeWithMaxFlow(team);
		}
		return certificates.get(team).getJ();
	}
	
	private void add4Trivial(int s) {
		List<String> iterables = new ArrayList<String>();
		int maxWinsPossible = maxWinsPossible(s);
		for(int i=0;i<teams.size();i++){
			if(i != s && wins[i] > maxWinsPossible ){
				iterables.add(int2teams.get(i));
			}
		}
		
		certificates.put(int2teams.get(s), new Tuple<Boolean, Iterable<String>>(true,iterables));
	}

	private boolean doTrivial(int index) {
		int possible = maxWinsPossible(index);
		for(int i = 0;i<wins.length;i++){
			if(wins[i] > possible){
				return true;
			}
		}
		
		return false;
	}

	private void computeWithMaxFlow(String team){
		int size = teams.size();
		FlowNetwork fn = new FlowNetwork(numOfPairsWithNonZerogames+size+1);
		int source = teams.get(team);
		double maxPossibleFlow = 0.0;
		
		int t = size;
		int v = t;
		
		for(int i=0;i<games.length;i++){
			if(i == source){
				continue;
			}
			FlowEdge team2t = new FlowEdge(i, t, getTeam2TargetCapacity(source, i));
			fn.addEdge(team2t);
			
			for(int j=0;j<games[i].length;j++){
				if(j != source && j >= i && games[i][j] != 0){
					FlowEdge s2Game = new FlowEdge(source, ++v, games[i][j]);
					fn.addEdge(s2Game);
					maxPossibleFlow += s2Game.capacity();
					
					addFromGameToTeam(fn, source, t, v, i);
					addFromGameToTeam(fn, source, t, v, j);
				}
			}
		}
		
		FordFulkerson ff = new FordFulkerson(fn, source, t);
		if(ff.value() < maxPossibleFlow){
			certificates.put(team, new Tuple<Boolean, Iterable<String>>(true, getIterables(ff,source)));
		}else{
			certificates.put(team, new Tuple<Boolean, Iterable<String>>(false, null));
		}
	}

	private List<String> getIterables(FordFulkerson ff, int source) {
		List<String> list = new ArrayList<String>();
		for(int team:int2teams.keySet()){
			if(team != source && ff.inCut(team)){
				list.add(int2teams.get(team));
			}
		}
		return list;
	}

	private void addFromGameToTeam(FlowNetwork fn, int source, int t,
			int v, int team) {
		FlowEdge game2team = new FlowEdge(v, team, Double.POSITIVE_INFINITY);
		fn.addEdge(game2team);
	}

	private int getTeam2TargetCapacity(int source, int i) {
		return maxWinsPossible(source)-wins[i];
	}

	private int maxWinsPossible(int source) {
		return wins[source]+remaining[source];
	}
	
	private void validate(String team) {
		if(!teams.containsKey(team)){
			throw new IllegalArgumentException(team);
		}
	}
	
	private void print(){
		for(Entry<String, Integer> entry:teams.entrySet()){
			int index = entry.getValue();
			System.out.format("%32s%3d%3d%3d%25s\n",entry.getKey(),wins[index],loses[index]
			  ,remaining[index],Arrays.toString(games[index]));
		}
	}
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team))
	                StdOut.print(t + " ");
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}
