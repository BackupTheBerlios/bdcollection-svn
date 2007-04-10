
package tools;

import java.util.ArrayList;

/**
 * Various methods to compute the similarity of two strings.
 *
 * 'comparePairSimilarity' uses an algorithm found on
 * http://www.catalysoft.com/articles/How_to_Strike_a_Match_15.html
 *
 * @version 1.0
 * $Date: 2004/05/13$<br>
 * @author Simon White$
 */

public class StringSimilarity
{
    
    /**
     * Compute similarity using the number of pair matches.
     *
     * @return similariry in range [0..1].
     */
    public static double comparePairSimilarity( String str1, String str2 )
    {
	ArrayList pairs1 = wordLetterPairs( str1.toUpperCase() );
	ArrayList pairs2 = wordLetterPairs( str2.toUpperCase() );

	int intersection = 0;
	int union = pairs1.size() + pairs2.size();

	for( int i=0; i < pairs1.size(); i++ ) {
	    Object pair1 = pairs1.get(i);
	    
	    for( int j=0; j < pairs2.size(); j++ ) {
		Object pair2 = pairs2.get(j);

		// if a matching pair is found, delete it and increment intersection
		if( pair1.equals( pair2 )) {
		    intersection ++;
		    pairs2.remove(j);
		    break;
		}
	    }
	}
	
	return (2.0 * intersection) / union;
    }

    /**
     * Compute pairs of letters.
     *
     * @return An array of adjacent letter pairs contained in the input String.
     */
    private static String[] letterPairs( String str ) 
    {
	int numPairs = str.length() - 1;
	String[] pairs = new String[numPairs];

	for( int i=0; i < numPairs; i++ ) {
	    pairs[i] = str.substring( i, i+2 );
	}

	return pairs;
    }

    /**
     * Take care of white spaces.
     *
     * @return An ArrayList of 2-character Strings.
     */
    private static ArrayList wordLetterPairs( String str ) 
    {
	ArrayList allPairs = new ArrayList();

	// tokenize the strinf and put the tokens/words into an array
	String[] words = str.split("\\s");
	// for each word
	for( int w=0; w < words.length; w++ ) {
	    // find the pairs of characters
	    String[] pairsInWord = letterPairs( words[w] );
	    
	    for( int p=0; p < pairsInWord.length; p++ ) {
		allPairs.add( pairsInWord[p] );
	    }
	}
	return allPairs;
    }

} // StringSimilarity
