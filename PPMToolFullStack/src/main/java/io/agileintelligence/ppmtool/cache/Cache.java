package io.agileintelligence.ppmtool.cache;

public interface Cache {
	void add(String key, Object value, long periodInMS);

	void remove(String key);

	void clear();

	long size();
	
	Object get(String key);
}
