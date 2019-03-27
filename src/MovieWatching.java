
public class MovieWatching {
	
	static Movie jumanji = new Movie("Jumanji", 4);
	static Movie jumanji2 = new Movie("Jumanji 2", 5);
	static Movie montyPython = new Movie("Monty Python and the Holy Grail", 5);
	static Movie LOTR = new Movie("Lord of the Rings", 5);
	static Movie infinityWar = new Movie("Infinity War", 3);
	static Movie frozen = new Movie("Frozen", 2);
	
	static NetflixQueue myQueue = new NetflixQueue();
	
	public static void main(String []args) {
		LOTR.getTicketPrice();
		
		myQueue.addMovie(jumanji);
		myQueue.addMovie(jumanji2);
		myQueue.addMovie(montyPython);
		myQueue.addMovie(LOTR);
		myQueue.addMovie(infinityWar);
		myQueue.addMovie(frozen);
		
		myQueue.sortMoviesByRating();
		myQueue.printMovies();
		
		System.out.println("The best movie is " + myQueue.getBestMovie());
		System.out.println("The second best movie is " + myQueue.getMovie(1));
	}
}