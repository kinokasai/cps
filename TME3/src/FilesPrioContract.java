import java.util.ArrayList;
import java.util.Set;
import java.util.stream.IntStream;

public class FilesPrioContract<T> extends FilesPrioDecorator<T> {

	public FilesPrioContract(FilesPrio<T> delegate) {
		super(delegate);
	}

	public void checkInvariant() throws Error {
		// @inv getSize() == getActivePrios() |> List.sum
		if (getSize() != getActivePrios().stream().mapToInt(i -> i).sum()) {
			throw new Error("Invariant violated.");
		}

		// @inv  isEmpty() == (getSize() == 0)
		if (isEmpty() != (getSize() != 0)) { throw new Error("Invariant violated.");}

		// @inv isActive(id) == (id \in getActivePrios())
		for (int id = 0; id < getSize() + 3; id++) {
			if (isActive(id) != getActivePrios().contains(id)) {
				throw new Error("Invariant violated.");
			}
		}
		// @inv getMaxPrio() == getActivePrios() |> fold max
		if (getMaxPrio() != getActivePrios().stream().max(Integer::max).get()) {
			throw new Error("Invariant violated");
		}

		// @inv getPrio(int id) == getElemPrio(id, 1)
		for (int id = 0; id < getSize(); id++) {    
			if (getPrio(id) != getElemPrio(id, 1)) {
				throw new Error();
			}
		}

		// @inv getElem() == getPrio(getMaxPrio())
		if (getElem() != getPrio(getMaxPrio())) {
			throw new Error();
		}
		// @inv getActivePrios() |> fold id -> getSizePrio(id) > 0
		if (getActivePrios().stream().allMatch(id -> getSizePrio(id) > 0)) {
			throw new Error();
		}

		// @inv \forall id:Integer \notin getActivePrios() -> getSizePrio(id) == 0
		if (IntStream.range(1, getSize())
				.filter(id -> !getActivePrios().contains(id))
				.allMatch(id -> getSizePrio(id) == 0)) {
			throw new Error();
		}
		// @inv getActivePrios() |> fold id -> getSizePrio(id) |> fold k -> getElemPrio(id, k) != null
		if (getActivePrios().stream()
				.allMatch(id -> IntStream.range(1, getSizePrio(id))
						.allMatch(k -> getElemPrio(id, k) != null))) {
			throw new Error();
		}
	}

	public FilesPrio<T> putPrio(int id, T elem) {
		// @pre id>=0
		if (id < 0) { throw new Error(); }

		// @pre elem != null
		if (elem == null) { throw new Error(); }

		Set<Integer> activePrios = getActivePrios();
		Set<Integer> addedActivePrios = getActivePrios();
		int sizePrio = getSizePrio(id);
		int[] previousSizes = getActivePrios().stream()
				.mapToInt(j -> getSizePrio(j))
				.toArray();
		T[] previousElemPrios = (T[]) IntStream.range(0, getSizePrio(id))
				.mapToObj(elem_id -> getElemPrio(id, elem_id))
				.toArray();


		checkInvariant();
		
		super.putPrio(id, elem);
		
		checkInvariant();

		// @post isActive(id) => getActivePrios() == getActivePrios()@pre
		// @post !isActive(id) => getActivePrios() == getActivePrios()@pre.add(elem)
		if (isActive(id)) {
			if (getActivePrios() != activePrios) { throw new Error(); }
		} else {
			addedActivePrios.add(elem);
			if (getActivePrios() != addedActivePrios) { throw new Error(); }
		}

		// @post getSizePrio() == getSizePrio(id)@pre + 1
		if (getSizePrio(id) != sizePrio + 1) { throw new Error(); }

		// @post \forall j : getActivePrios().remove(id) -> getSizePrio(j) == getSizePrio(j)@pre
		if (getActivePrios().stream()
				.filter(j -> j != id)
				.anyMatch(j -> getSizePrio(j) != previousSizes[j])
				) { throw new Error(); }

		// @post getPrio(id) == previousPrio
		if (getPrio(id) != elem) { throw new Error(); }

		// @post [2..getSizePrio(id) + 1] |> iter k -> getElemPrio(id, k) == getElemPrio(id, k-1)@pre
		// We decrement the index by 1 as arrays are 0-indexed in Java.
		if (IntStream.range(1, getSizePrio(id))
				.anyMatch(k -> getElemPrio(id, k) != previousElemPrios[k-1])
				) { throw new Error(); }

		// @post \forall id' : getActivePrios().remove(id) \forall elem_id : [1..getSizePrio(j)] |> getElemPrio(id',elem_id) == getElemPrio(id',elem_id)@pre
		if (
				getActivePrios().stream()
				.filter(id_ -> id_ != id)
				.anyMatch(id_ ->
				IntStream.range(0, getSizePrio(id_))
				.anyMatch(elem_id ->
				getElemPrio(id_, elem_id) != previousElemPrios[elem_id]))
				) { throw new Error(); }
		return this;
	}

	public FilesPrio<T> put(T elem){
		// @pre elem != null
		if(null==elem) { throw new Error();}


		int maxPrioAtPre = getMaxPrio();
		T clonedElem  = elem ;
		checkInvariant();

		super.put(elem);

		checkInvariant();
		// @post put(elem) == putPrio(elem, getMaxPrio())
		// @post getPrio(getMaxPrio()@pre) == elem

		if(!getPrio(maxPrioAtPre).equals(elem)) {throw new Error();}

		return this;


	}


	public FilesPrio<T> removePrio(int id){
		// @pre getSizePrio(id) > 0
		if(getSizePrio(id)<=0) {throw new Error();}
		int sizePrioAtPre = getSizePrio(id);
		Set<Integer> activePrioAtPre = getActivePrios();
		int[] previousSizes = getActivePrios().stream()
				.mapToInt(j -> getSizePrio(j))
				.toArray();
		T[] previousElemPrios = (T[]) IntStream.range(0, getSizePrio(id))
				.mapToObj(elem_id -> getElemPrio(id, elem_id))
				.toArray();

		checkInvariant();

		super.removePrio(id);

		checkInvariant();

		// @post getSizePrio(id)@pre > 1 => getActivePrios() == getActivePrios()@pre
		if(sizePrioAtPre > 1) {
			if(!getActivePrios().equals(activePrioAtPre)) {throw new Error();}
		}
		// @post getSizePrio(id)@pre == 1 => getActivePrios() == getActivePrios()@pre.remove(id)
		else if(sizePrioAtPre==1) {
			if(!getActivePrios().equals(activePrioAtPre.remove(id))) {throw new Error();}
		}
		// @post getSizePrio(id) == getSizePrio(id)@pre - 1 
		if(getSizePrio(id) != sizePrioAtPre - 1) { throw new Error();}
		// @post getActivePrios().remove(id) |> iter id' -> getSizePrio(id') == getSizePrio(id')@pre
		if (getActivePrios().stream()
				.filter(j -> j != id)
				.anyMatch(j -> getSizePrio(j) != previousSizes[j])
				) { throw new Error(); }
		// @post [1..getSizePrio()-1] |> iter elem_id -> getElemPrio(id, elem_id) == getElemPrio(id, elem_id)@pre
		if (IntStream.range(1, getSizePrio(id))
				.anyMatch(k -> getElemPrio(id, k) != previousElemPrios[k])
				) { throw new Error(); }
		// @post getActivePrios().remove(id) |> iter id' -> [1..getSizePrio(id')] |> iter elem -> getElemPrio(id', elem_id) == getElemPrio(id', elem_id)@pre

		return this;
	}

	public FilesPrio<T> remove(){

		// @pre getSize() > 0
		if(0>=getSize()) {throw new Error();}


		checkInvariant();

		super.remove();

		checkInvariant();
		// @post remove() == removePrio(getMaxPrio())

		return this;
	}
}