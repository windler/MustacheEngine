package de.windler.engine.mustacheengine.pool;

import java.util.Stack;

/**
 * Object-Pool are very neccessary to prevent that objects are created while
 * runtime!
 * 
 * @author Nico Windler
 * 
 * @param <T>
 */
public abstract class ObjectPool<T> {

	private Stack<T> poolObjects;
	private int growOnEmpty;

	/**
	 * empty pool
	 */
	public ObjectPool() {
		this(0, 1);
	}

	/**
	 * 
	 * @param poolSize
	 *            starting count of objects
	 * @param grow
	 *            If new objects are needed how many should be added?
	 */
	public ObjectPool(int poolSize, int grow) {
		poolObjects = new Stack<T>();
		allocatePoolItems(poolSize);
		growOnEmpty = grow;
	}

	/**
	 * {@code onHandleAllocatePoolItems()}
	 * 
	 */
	public void allocatePoolItems(int count) {
		for (int i = count; i > 0; i--) {
			poolObjects.push(onHandleAllocatePoolItems());
		}
	}

	/**
	 * Objects which will be put on stack when new PoolObjects are created
	 * 
	 */
	protected abstract T onHandleAllocatePoolItems();

	public T getPoolItem() {
		if (poolObjects.isEmpty()) {
			allocatePoolItems(growOnEmpty);
		}

		return poolObjects.pop();
	}

	/**
	 * {@code onHandleRecyclePoolObject()}
	 */
	public void recyclePoolObject(T object) {
		onHandleRecyclePoolObject(object);
		poolObjects.push(object);
	}

	/**
	 * What to do when objects are recyled? Maybe set default values?
	 * 
	 */
	protected abstract void onHandleRecyclePoolObject(T object);

}
