# 23-Tree
2-3 Tree Implemented as a left leaning red black tree

For practice with data structures I implemented a "2-3" tree as a left leaning red-black tree. A 2-3 tree is a form of balanced search tree similar to a binary tree where some nodes have 2 keys and 3 children instead of just 1 key and 2 children. For implementation, rather than each node having a variable number of keys, we create a red-black tree: a binary tree where nodes can either be colored red or or black. A red node represents a node with two keys for our 2-3 tree, the other key being the key of its parent. The tree maintains the property that all red nodes are left-children of their parents, no child of a red node is a red node, and the number of black nodes traversed from root to leaf is constant for any search. The second two properties guarantee O(log n) performance for search and insert in the worst case.

The program is run from RedBlackTree.java and takes command line input to insert 1 key at a time, clear the current tree, or print out a DFS/BFS of the keys in the tree. The DFS will return the keys in sorted order, the BFS will print BLACK level order traverse, so that red nodes are considered on the same level as their parent node. It is modified to print two such nodes as [k1, k2] to match the intent of the data structure. Within the data structure, k1 is a red node which is the left child of k2. The left branch of k1 will contain keys smaller than k1; the right branch of k1 will contain keys between k1 and k2; and the right child of k2 will contain keys strictly larger than k2. The program currently ignores duplicate keys and does not support a remove function.

COMMAND LINE INTERFACE:

Press 1 to insert

Press 2 to print DFS

Press 3 to print BFS

Press 4 to clear tree

Press 5 to quit


SAMPLE OUTPUT FOR BFS:

-2  
[-8,-5]   4  
-9   -7   [-4,-3]   2   [5,7]

Here -2 is the root. Its left child is -5 which has a RED left child with key -8. This gets printed as a node with 2 keys and 3 children to show the balance of the tree. The black distance from root to leaf is constant on any path, and the total distance is at most 2*black distance as no red node has a red child.
