package tree;

public interface ListI<T> extends BTreeI<T>{ 
    public T data();
    public List<T> tail();
    public List<T> parent();
    public int length();
    public void setData(T data);
    public void update(int i, T data);
    public void setTail(List<T> l);
    public void add (int pos, T data);
    public List<T> remove(int i);
    public List<T> copy();
    public String toString();
    public void display();

    /* Les méthodes suivantes sont ajoutées pour que les listes fonctionnent pour les fonctions acceptant des arbres
     * mais ne sont pas utiles en général.
     */
     
    public List<T> left();
    public List<T> right();
    public void setLeft(List<T> t);
    public List<T> child(int i);
    public int nbChildren();
    @SuppressWarnings("unchecked")
    public void addChildren(List<T>... childs);
    public void setChild(int i, List<T> child);
}