# Huffman coding

In this extended lab exercise you will implement Huffman coding. This is an elegant 
compression algorithm. The idea is to generate a binary sequence that represents each character 
required. This might be the English alphabet, some subset of that, or any 
collection of symbols. 

Say we have a 100KB file made up of repetitions of the letters "a" to "f".
We start by creating a frequency table:

![Frequency table](etc/images/ftable.png)

If we use a fixed-length code we can encode this data in about
37.5KB. If we use a variable-length code and assign the shortest
code to the most frequently used characters, we can encode it in
just 28KB.

![Fixed and variable length codes](etc/images/codes.png)

We can create these variable-length codes using a binary tree (not
a search tree). In a Huffman Tree the leaves contain the data, a
character and its frequency. Internal nodes are labelled with the
combined frequencies of their children.

![Fixed and variable length codes](etc/images/htree.png)

To decode data we go start at the root and go left for 0, and right
for 1 until we get to a leaf. So, to decode `0101100`, we start at the root and
after consuming a single digit from the code, `0`, we reach a leaf labelled `a`. We return 
to the root and take digits from the code until we reach another leaf, which is labelled `b`,
and so on.

The most elegant part of this scheme is the algorithm used to create the tree:

1. Make a tree node object for each character, with an extra label
for its frequency.
2. Put these nodes in a priority queue, where the lowest
frequency has highest priority.
3. Repeatedly:
    - Remove two nodes from the queue and insert them as children
to a new node. The char label of the new node is blank and
the frequency label is the sum of the labels of its children.
    - Put the new node back in the queue.
    - When there is only one item in the queue, that's the Huffman
tree.

## Exercises

1. First, implement the *priority queue* in the class `PQueue`. 