# Getting Started

To test it with your own sample.json file, please put the file into src/test/resources/data folder.

To run the program, go to Class NumberFinderImplTests.

Run testContains_shouldReturnTrue() or testContains_shouldReturnFalse() methods 
and change the valueToFind and filePath to your fit.

Some confusing stuff:
In the task description, it says FastestComparator is a comparator used to sort CustomNumberEntity objects and its compare method cannot be modified.
As it is time-consuming and best sorting time complexity is O(nlogn). Once sorted, we can use the binary search to find the target element. 
The overall time complexity will be O(nlogn + logn). If we just use sequence search, its time complexity will be O(n), which is better.

So I don't think we should use the sorting for the list. If the sorting is required, we should use the binary search to find the target, 
which is coded in containsByBinarySearch method. 

Note:
If we only sort the list once, and search multiple elements based on this list. The containsByBinarySearch will be the best option!
