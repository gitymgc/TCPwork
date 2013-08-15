package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

class FileLogger implements Logger{

	PrintWriter out;

	public FileLogger(String fileName)throws IOException{
		out = new PrintWriter(new FileWriter(fileName), true);
	}

	public synchronized void writeEntry(Collection entry){
		for(Iterator line = entry.iterator(); line.hasNext();){
			out.println(line.next());
		}
		out.println();
	}

	public synchronized void writeEntry(String entry){
		out.println(entry);
		out.println();
	}

}
