// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.
import java.util.*;
public class A2DynamicMem extends A1DynamicMem
{
      
    public A2DynamicMem()
    {  
        super(); 
    }

    public A2DynamicMem(int size) 
    {
        super(size); 
    }

    public A2DynamicMem(int size, int dict_type) 
    {   
        super(size, dict_type);
    }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 

    public int Allocate(int blockSize)
    {   
        if(blockSize<0)
        {
            return -1;
        }
        if(freeBlk.Find(blockSize,false)!=null)
        {
            Dictionary temp=freeBlk.Find(blockSize,false);
            if(temp.size== blockSize)
            {
                allocBlk.Insert(temp.address, blockSize, temp.address);
                freeBlk.Delete(temp);
                return temp.address;
            }
            if(temp.size>blockSize)
            {
                allocBlk.Insert(temp.address, blockSize, temp.address);
                freeBlk.Delete(temp);
                freeBlk.Insert(temp.address + blockSize, temp.size - blockSize, temp.size - blockSize);
                return temp.address;
            }
        }
        return -1;
    } 
    // return 0 if successful, -1 otherwise
    public int Free(int startAddr)
    {
        Dictionary temp = allocBlk.Find(startAddr,true);
        if(startAddr<0)
        {
            return -1;
        }
        else
        {
            if(temp!=null)
            {
                freeBlk.Insert(temp.address, temp.size, temp.size);
                allocBlk.Delete(temp);
                return 0;
            }
            return -1;
        }
        
    }
    
    public void Defragment()
    {
        if(this.type==2)
        {
            Dictionary defragBlk = new BSTree();

            if(freeBlk.getFirst()==null)
            {
                return;
            }
            Dictionary x = freeBlk.getFirst();
            while(x!=null)
            {
                defragBlk.Insert(x.address,x.size,x.address);
                x=x.getNext();
            }
            Dictionary y = defragBlk.getFirst();
            Dictionary z = y;
            while(y.getNext()!=null)
            {
                z = y.getNext();
                if(y.address+y.size==z.address)
                {
                    Dictionary temp = new BSTree(y.address,y.size,y.size);
                    Dictionary temp2 = new BSTree(z.address,z.size,z.size);
                    defragBlk.Delete(y);
                    defragBlk.Delete(z);
                    defragBlk.Insert(temp.address,temp.size+temp2.size,temp.address);
                    freeBlk.Delete(temp);
                    freeBlk.Delete(temp2);
                    freeBlk.Insert(temp.address,temp.size+temp2.size,temp.size+temp2.size);
                    y=z;
                }
                else
                {
                    y=z;
                }
            }
            return;
        }
        else if(this.type==3)
        {
            Dictionary defragBlk = new AVLTree();

            if(freeBlk.getFirst()==null)
            {
                return;
            }
            Dictionary x = freeBlk.getFirst();
            while(x!=null)
            {
                defragBlk.Insert(x.address,x.size,x.address);
                x=x.getNext();
            }
            Dictionary y = defragBlk.getFirst();
            Dictionary z = y;
            while(y.getNext()!=null)
            {
                z = y.getNext();
                if(y.address+y.size==z.address)
                {
                    Dictionary temp = new AVLTree(y.address,y.size,y.size);
                    Dictionary temp2 = new AVLTree(z.address,z.size,z.size);
                    defragBlk.Delete(y);
                    defragBlk.Delete(z);
                    defragBlk.Insert(temp.address,temp.size+temp2.size,temp.address);
                    freeBlk.Delete(temp);
                    freeBlk.Delete(temp2);
                    freeBlk.Insert(temp.address,temp.size+temp2.size,temp.size+temp2.size);
                    y=z;
                }
                else
                {
                    y=z;
                }
            }
            return;
        }
    }
}


