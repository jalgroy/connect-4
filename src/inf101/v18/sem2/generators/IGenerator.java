package inf101.v18.sem2.generators;

import java.util.List;
import java.util.Random;

/**
 * An interface for generators of random data values (to be used in testing).
 *
 * @param <T> The type of data generated.
 */
public interface IGenerator<T> {

	/**
	 * Generate a random object.
	 *
	 * @param r A random generator
	 * @return An object of type T
	 */
	T generate(Random r);

	/**
	 * Generate a number of equal objects.
	 *
	 * @param r A random generator
	 * @param n The number of objects to generate.
	 * @return A list of objects, with the property that for any two objects a,b in the collection a.equals(b).
	 *
	 */
	List<T> generateEquals(Random r, int n);

	/**
	 * Generate a number of equal objects.
	 * 
	 * @param n The number of objects to generate.
	 * @return A list of objects, with the property that for any two objects a,b in the collection a.equals(b).
	 * 
	 */
	List<T> generateEquals(int n);

	/**
	 * Generate a random object.
	 * 
	 * @return An object of type T
	 */
	T generate();
}
