import java.util.Set;

public abstract class FilesPrioDecorator<T> implements FilesPrio<T> {
    private FilesPrio<T> delegate;

    protected FilesPrioDecorator(FilesPrio<T> delegate) {
        this.delegate = delegate;
    }
    public void init() { delegate.init(); }

    /* observators */
    public int getSize() { return delegate.getSize();}
    public boolean isEmpty() { return delegate.isEmpty();}
    public boolean isActive(int id) { return delegate.isActive(id);}
    public Set<Integer> getActivePrios() { return delegate.getActivePrios();}
    public int getMaxPrio() { return delegate.getMaxPrio();}
    public int getSizePrio(int id) { return delegate.getSizePrio(id);}
    // @pre getSizePrio(int id) > 0
    public T getPrio(int id) { return delegate.getPrio(id);}
    // @pre getSize() > 0
    public T getElem() { return delegate.getElem();}
    // @pre \forall id \in getActivePrios()
    // @pre 0 < id <= sizePrio(id)
    public T getElemPrio(int id, int elem_id) { return delegate.getElemPrio(id, elem_id);}

    /* constructors */
    // @post getSize(init()) == 0

    /* operators */
    // @pre id>=0
    // @pre elem != null
    // @post isActive(id) => getActivePrios() == getActivePrios()@pre
    // @post !isActive(id) => getActivePrios() == getActivePrios()@pre.add(elem)
    // @post getSizePrio() == getSizePrio(id)@pre + 1
    // @post \forall j : getActivePrios().remove(id) -> getSizePrio(j) == getSizePrio(j)@pre
    // @post getPrio(id) == getPrio(id+1)@pre
    // @post [2..getSizePrio(id) + 1] |> iter k -> getElemPrio(id, k) == getElemPrio(id, k-1)@pre
    // @post \forall id' : getActivePrios().remove(id) \forall elem_id : [1..getSizePrio(j)] |> getElemPrio(id',elem_id) == getElemPrio(id',elem_id)@pre
    public FilesPrio<T> putPrio(int id, T elem) { return delegate.putPrio(id, elem);}
    // @pre elem != null
    // @post put(elem) == putPrio(elem, getMaxPrio())
    public FilesPrio<T> put(T elem) { return delegate.put(elem);}
    // @pre getSizePrio(id) > 0
    // @post getSizePrio(id)@pre > 1 => getActivePrios() == getActivePrios()@pre
    // @post getSizePrio(id)@pre == 1 => getActivePrios() == getActivePrios()@pre.remove(id)
    // @post getSizePrio(id) == getSizePrio(id)@pre - 1 
    // @post getActivePrios().remove(id) |> iter id' -> getSizePrio(id') == getSizePrio(id')@pre
    // @post [1..getSizePrio()-1] |> iter elem_id -> getElemPrio(id, elem_id) == getElemPrio(id, elem_id)@pre
    // @post getActivePrios().remove(id) |> iter id' -> [1..getSizePrio(id')] |> iter elem -> getElemPrio(id', elem_id) == getElemPrio(id', elem_id)@pre
    public FilesPrio<T> removePrio(int id) { return delegate.removePrio(id);}
    // @pre getSize() > 0
    // @post remove() == removePrio(getMaxPrio())
    public FilesPrio<T> remove() { return delegate.remove();}

    /* observations */
    /* Invariants */
    // @inv getSize() == getActivePrios() |> List.sum
    // @inv isEmpty() == (getSize() == 0)
    // @inv isActive(id) == (id \in getActivePrios())
    // @inv getMaxPrio() == getActivePrios() |> fold max
    // @inv getPrio(int id) == getElemPrio(id, 1)
    // @inv getElem() == getPrio(getMaxPrio())
    // @inv getActivePrios() |> fold id -> getSizePrio(id) > 0
    // @inv \forall id:Integer \notin getActivePrios() -> getSizePrio(id) == 0
    // @inv getActivePrios() |> fold id -> getSizePrio(id) |> fold k -> getElemPrio(id, k) != null

}