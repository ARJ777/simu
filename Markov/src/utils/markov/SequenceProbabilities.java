package utils.markov;

/**
 * This interface provides an encapsulation of one sequence
 * of observations, in the form of either p(x_t|s_t)
 * or P(s_t|x_t), for stages t=1,2,...,T.
 * <p/>
 * Note that the SequenceType of the sequence is also specified,
 * since this provides additional information about the relevant
 * boundary conditions.
 */
public interface SequenceProbabilities {

	enum SequenceType { 
		start /* A sub-sequence known to start at stage t=1. */, 
		end   /* A sub-sequence known to end at stage t=T. */, 
		full  /* A complete sequence known to start at stage t=1 and end at stage t=T. */, 
		sub   /* A partial sub-sequence. */;

		boolean isInitiated() {
			return (this == start || this == full);
		}
		
		boolean isTerminated() {
			return (this == end || this == full);
		}
		

	};

	public enum ProbabilityType {
		likelihood, posterior
	};
	
	/**
	 * @return The number T of stages in the sequence. 
	 */
	int numStages();
	
	/**
	 * @return The number S of states used to model the data.
	 */
	int numStates();
	
	/**
	 * @return The type of observed sequence, indicating whether
	 * or not the initial (i.e. stage 1) and/or final 
	 * (i.e. stage T) boundaries are to be treated specially.
	 */
	SequenceType sequenceType();
	
	/**
	 * @return The type of probabilities given for the observed sequence.
	 */
	ProbabilityType probabilityType();
	
	/**
	 * @param t - The desired stage in the sequence, 1 <= t <= T.
	 * @return Either p(x_t|s_t) or P(s_t|x_t), depending upon the probability type.
	 */
	double[] stageVector(int t);
	
}
