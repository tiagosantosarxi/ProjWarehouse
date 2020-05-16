package search;

import agentSearch.Problem;
import agentSearch.Solution;

public interface SearchMethod {

   Solution search(Problem problem) throws CloneNotSupportedException;
   
   Statistics getStatistics();
   
   void stop();
   
   boolean hasBeenStopped();
}