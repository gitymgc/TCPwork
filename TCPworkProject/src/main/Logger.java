package main;

import java.util.Collection;

public interface Logger {
	public void writeEntry(Collection entry);
	public void writeEntry(String entry);
	
}
