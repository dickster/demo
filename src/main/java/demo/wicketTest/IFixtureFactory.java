package demo.wicketTest;

public interface IFixtureFactory<T> {
	public T createFixture(String id); 
}

