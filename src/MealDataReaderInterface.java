// --== CS400 File Header Information ==--
// Name: Raea Freund
// Email: rfreund2@wisc.edu
// Team: DF
// TA: Dan Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;
import java.util.LinkedList;

// interface to implement meal reader 
public interface MealDataReaderInterface {
	// method called by backend to retrieve list of constructed menu items.
	public LinkedList<Item> readDataSet(String menu) throws FileNotFoundException;
}
