import java.util.ArrayList;
import java.util.Set;

public class FilePrioImpl<T> implements FilesPrio<T>{
	private ArrayList<T> priorites;
	private Set<Integer> activePrio;
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return priorites.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return priorites.isEmpty();
	}

	@Override
	public boolean isActive(int id) {
		// TODO Auto-generated method stub
		return activePrio.contains(id);
	}

	@Override
	public Set<Integer> getActivePrios() {
		// TODO Auto-generated method stub
		return activePrio;
	}

	@Override
	public int getMaxPrio() {
	    Integer tmp = 0;
	    for(Integer test : activePrio) {
	        if(test>tmp) {
	            tmp = test;
	        }
	    }
		// TODO Auto-generated method stub
		return tmp ;
	}

	@Override
	public int getSizePrio(int id) {
		// TODO Auto-generated method stub
		return activePrio.size();
	}

	@Override
	public T getPrio(int id) {
		// TODO Auto-generated method stub
		return priorites.get(id);
	}

	@Override
	public T getElem() {
		// TODO Auto-generated method stub
		return priorites.get(getSize()-1);
	}

	@Override
	public T getElemPrio(int id, int elem_id) {
		// TODO Auto-generated method stub
		return priorites.get(id);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		priorites = new ArrayList<>();
		
	}

	@Override
	public FilesPrio<T> putPrio(int id, T elem) {
		// TODO Auto-generated method stub
		priorites.set(id, elem);
		return this;
	}

	

	@Override
	public FilesPrio<T> removePrio(int id) {
		// TODO Auto-generated method stub*
		priorites.remove(id);
		return this;
	}

	@Override
	public FilesPrio<T> put(T elem) {
		// TODO Auto-generated method stub
		return putPrio(getMaxPrio(), elem);
	}

	@Override
	public FilesPrio<T> remove() {
		// TODO Auto-generated method stub
		return removePrio(getMaxPrio());
	}

	

	

}
