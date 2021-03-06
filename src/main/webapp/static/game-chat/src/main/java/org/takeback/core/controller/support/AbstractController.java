package org.takeback.core.controller.support;

import com.google.common.collect.Maps;
import org.takeback.core.controller.Configurable;
import org.takeback.core.controller.ConfigurableLoader;
import org.takeback.core.controller.Controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractController<T extends Configurable> implements Controller<T> {
	protected final Map<String, T> store = Maps.newHashMap();
	protected ConfigurableLoader<T> loader;
	private final Lock lock = new ReentrantLock();

	@Override
	public void setLoader(ConfigurableLoader<T> loader) {
		this.loader = loader;
	}

	@Override
	public ConfigurableLoader<T> getLoader() {
		return loader;
	}

	@Override
	public boolean isLoaded(String id) {
		try {
			lock.lock();
			return store.containsKey(id);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void reload(String id) {
		try {
			lock.lock();
			store.remove(id);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void reloadAll() {
		store.clear();
	}

	@Override
	public T get(String id) {
		try {
			lock.lock();
			if (store.containsKey(id)) {
				return store.get(id);
			}
		} finally {
			lock.unlock();
		}

		try {
			lock.lock();
			if (store.containsKey(id)) {
				return store.get(id);
			} else {
				T t = loader.load(id);
				if (t != null) {
					store.put(id, t);
					return t;
				} else {
					return null;
				}
			}
		} finally {
			lock.unlock();
		}
	}

	public void add(T t) {
		try {
			lock.lock();
			store.put(t.getId(), t);
		} finally {
			lock.unlock();
		}
	}

	public void setInitList(List<T> ls) {
		for (T t : ls) {
			add(t);
		}
	}

}
