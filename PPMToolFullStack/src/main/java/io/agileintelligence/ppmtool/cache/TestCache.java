package io.agileintelligence.ppmtool.cache;

public class TestCache {

	public static void main(String[] args) {
		CacheImpl cache = new CacheImpl();
		cache.add("1001", "Anuj2", 2);
		cache.add("1002", "Anuj2", 2);
		cache.add("1003", "Anuj3", 2);
		cache.add("1004", "Anuj4", 2);
		cache.add("1005", "Anuj5", 2);
		System.out.println("Cache: "+cache);

	}

}
