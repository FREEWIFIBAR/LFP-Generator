package Generator;

import java.io.*;
import java.util.*;

public class ClauseWriter {

	public static void write(List<Clause> clauses, String filename) throws IOException {

		PrintWriter pw = new PrintWriter(filename);

		for (Clause c : clauses) {

			pw.println(c);
		}

		pw.close();
	}
}
