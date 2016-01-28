package com.synacktiv;

import java.util.List;
import java.util.ArrayList;

import com.beust.jcommander.Parameter;
 
public class CommandLine {
	@Parameter(names = {"-h", "-help"}, description = "Display this message", help = true)
	public boolean help;

	@Parameter(names = "-pretty", description = "Pretty print")
	public boolean pretty = false;

	@Parameter(names = "-raw", description = "Raw output")
	public boolean raw = false;

	@Parameter(names = "-outfile", description = "Patched output file (default: stdout)", arity = 1)
	public String outfile = null;

	@Parameter(names = "-rawpatch", description = "Patch blockdata (<start_offset> <end_offset> <blockdata_file>)", arity = 3)
	private List<String> rawpatches = new ArrayList<String>();

	@Parameter(names = "-patch", description = "Patch class field (<obj_addr> <field_name> <value>)", arity = 3)
	private List<String> patches = new ArrayList<String>();

	@Parameter(description = "<viewstate_file>", arity = 1)
	public List<String> args = new ArrayList<String>();

	public int getPatchCount() {
		return patches.size() / 3;
	}

	public String[] getPatch(int index) {
		String addr = patches.get(index*3);
		String field = patches.get(index*3+1);
		String value = patches.get(index*3+2);

		return new String[] {addr, field, value};
	}

	public int getRawPatchCount() {
		return rawpatches.size() / 3;
	}

	public String[] getRawPatch(int index) {
		String addr = rawpatches.get(index*3);
		String field = rawpatches.get(index*3+1);
		String value = rawpatches.get(index*3+2);

		return new String[] {addr, field, value};
	}
}

