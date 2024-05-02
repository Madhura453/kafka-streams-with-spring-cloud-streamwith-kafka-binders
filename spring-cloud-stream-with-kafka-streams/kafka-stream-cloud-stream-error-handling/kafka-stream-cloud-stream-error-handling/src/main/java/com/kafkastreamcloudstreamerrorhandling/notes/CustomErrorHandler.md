- If the records have values that don't match either of the predicates provided in the branch method calls 
- (i.e., values that don't contain "FIRST" or "SECOND"), those records won't be processed further according to the 
- logic provided in the code snippet.

- In other words, records with values like "third" or "fourth" won't be routed to any branch and won't undergo 
- any additional processing specified after the noDefaultBranch() call. They'll essentially be ignored or 
- not processed further in the Kafka Streams topology.

````
       orderBranchingProcessor-in-0:
         destination: raw-order-topic
       orderBranchingProcessor-out-0:
         destination: order-topic
       orderBranchingProcessor-out-1:
         destination: errored-order
````
````
  .branch(isSuccessful)
                    .branch(isFailure)
````
- Here 2 out is we declared 2 branches. 2 outs.
- same applicable for  upperCaseBranchingProcessor