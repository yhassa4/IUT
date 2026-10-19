package tree;

import java.util.ArrayList;

public class Tree<T> implements TreeI<T>{

    private T data;
    private ArrayList<Tree<T>> children;
    private Tree<T> parent;
    private boolean isEmpty;

    public Tree(){
        isEmpty = true;
    }

    @SafeVarargs
    public Tree(T data, Tree<T>... childs) {
        this.data = data;
        this.children = new ArrayList<Tree<T>>();
        this.addChildren(childs);
        this.parent = null;
    }

    public T data(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("This tree is empty");
        }
        return this.data;
    }
    
    public void setData(T data){
        this.data = data;
        if (this.isEmpty()){
            isEmpty = false;
            this.children = new ArrayList<Tree<T>>();
        }
    }

    public Tree<T> child(int n) {
        if (this.isEmpty()){
            throw new IllegalArgumentException("This tree is empty");
        }
        try{
            return this.children().get(n);
        }
        catch (IndexOutOfBoundsException e){
            return new Tree<T>();
        }
    }

    private ArrayList<Tree<T>> children(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("This tree is empty");
        }
        return this.children;
    }

    public int nbChildren(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("This tree is empty");
        }
        return this.children().size();
    }

    public Tree<T> parent(){
        return this.parent;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    @SafeVarargs
    public final void addChildren(Tree<T>... childs) {
        for (Tree<T> child : childs){
            if (child != null){
                child.parent = this;
                this.children.add(child);
            }
        }
    }

    /* Attention : la méthode suivante ne peut que modifier un enfant existant */
    public void setChild(int i, Tree<T> child) {
        this.children.set(i, child);
        if (child != null){
            child.parent = this;
        }
    }

    public void remove(){
        if (this.parent() == null){
            throw new IllegalArgumentException("The root cannot be removed.\n");
        }
        /* Merci à Mathieu pour le rapport de bug */
        for(int i = 0; i < this.parent().nbChildren(); i++){
           if (this.parent().child(i) == this){
                this.parent().children().remove(i);
                /* C'est la méthode remove de la classe Arraylist, pas un appel récursif */
           }
        }
    }

     /* Adapted from VasiliNovikov@StackOverflow */

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        if (this.isEmpty) return;
        buffer.append(prefix);
        buffer.append(this.data());
        buffer.append('\n');
        for (int i = 0; i < nbChildren(); i++) {
            Tree<T> next = this.child(i);
            if (i < nbChildren() -1) {
                //next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
                next.print(buffer, childrenPrefix + "|-- ", childrenPrefix + "|   ");
            } else {
                //next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
                next.print(buffer, childrenPrefix + "|-- ", childrenPrefix + "    ");
            }
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    public void display() {
        System.out.println(this.toString());
    }
}