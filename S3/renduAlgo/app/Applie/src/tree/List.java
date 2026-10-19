package tree;

import java.util.Arrays;

public class List<T> extends BTree<T> implements ListI<T> {
    
    private T data;
    private List<T> tail;
    private List<T> parent;

    @SafeVarargs
    public List(T head, T... data) {
        this.data = head;
        if (data.length == 0){
            this.tail = null;
        }
        else{
            this.tail = new List<T>(data[0], Arrays.copyOfRange(data, 1, data.length));
            this.parent = null;
            this.tail.parent = this;
        }
    }
    public List() {
        this.data = null;
        this.tail = null;
        this.parent = null;
    }

    public List(T head, List<T> tail) {
        this.data = head;
        this.tail = tail;
        if (this.tail != null){
            this.tail.parent = this;
        }
    }

    public T data(){
        return this.data;
    }

    public List<T> tail(){
        return this.tail;
    }

    public int length(){
        if (this.tail() == null){return 1;}
        return 1+ this.tail().length();
    }

    public T get(int i) throws IllegalArgumentException{
        if (i < 0){
            throw new IllegalArgumentException();
        }
        if (i == 0){
            return this.data();
        }
        else{
            return this.tail().get(i-1);
        }
    }

    public List<T> parent(){
        return this.parent;
    }

    public void setData(T data){
            this.data = data;
    }

    public void update(int i, T data){
        if (i==0){
            this.setData(data);
        }
        else{
            if (this.tail() == null){
                throw new IndexOutOfBoundsException();
            }
            this.tail().update(i-1, data);
        }
    }

    public void setTail(List<T> l){
        this.tail = l;
        if (l != null){
            this.tail().parent=this;
        }
    }

    
    /* Retire l'élément numéro i de la liste.
    * Attention, retirer le dernier élément de liste ne fait rien à la liste
    * (puisque this ne peut pas être null), mais renvoie bien null en valeur de sortie.
    * C'est sans doute un point de l'implémentation à critiquer...
    */
    public List<T> remove(int pos){
        if (pos < 0){
            return this.remove(pos + this.length());
        }
        if (pos == 0 && this.tail() == null){
            return null;
        }
        if (pos == 0 && this.tail() != null){
            this.setData(this.tail().data());
            this.setTail(this.tail().tail());
            return this;
        }
        else{
            this.setTail(this.tail().remove(pos-1));
            return this;
        }
    }

    /* A method to add an element to a list at position i.
     * pos 0 adds at the start, 
     * pos -1, or pos >= this.length, adds at the end.
     */
    public void add(int pos, T data){
        if (pos < 0){
            this.add(pos + this.length(), data);
            return;
        }
        if (pos == 0){
            this.setTail(this.copy());
            this.setData(data);
            return;
        }
        if (this.tail() == null)
        {
            this.setTail(new List<T>(data));
            return;
        } 
        else{
            this.tail().add(pos-1, data);
        }
        return;
    }

    public List<T> copy(){
        if (this.tail() == null){
            return new List<T>(this.data());
        }
        return new List<T>(this.data(), this.tail().copy());
    }

    public String toString(){
        if (this.tail == null){
            return this.data().toString();
        }
        else{
            return this.data().toString() + ";" + this.tail().toString();
        }
    }

    public void display(){
        System.out.println(this.toString());
    }
    /* Pour la compatibilité avec BTree<T> */

    public List<T> left(){
        return this.tail();
    }

    public List<T> right(){
        return null;
    }

    public void setLeft(List<T> child){
        setTail(child);
    }

    public List<T> child(int i){
        if (i == 0) return this.tail();
        else return null;
    }

    public int nbChildren(){
        if (this.tail() == null) return 0;
        else return 1;
    }

    @SafeVarargs
    public final void addChildren(List<T>... childs){
        this.setTail(childs[0]);
    }

    public void setChild(int i, List<T> child){
        if (i==0) setTail(child);
        else throw new IndexOutOfBoundsException();
    }

}