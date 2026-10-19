package tree;

public interface TreeI<T>{
    public T data();
    public Tree<T> child(int i);
    public int nbChildren();
    public Tree<T> parent();
    @SuppressWarnings("unchecked")
    public void addChildren(Tree<T>... childs);
    public void setChild(int i, Tree<T> child);
    public void remove();
    public String toString();
    public void display();
}