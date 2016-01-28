/// SYNACKTIV: patch class
package com.synacktiv;

// TODO: sc_type ?
// TODO: len ?

public class Patch {
	// offset to patch
	private int startOffset;
	private int endOffset;
	// value to insert
	private Object value;

	public Patch(int startOffset, int endOffset, Object value) {
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public String toString() {
		return "patch (s_offset="+startOffset+" / e_offset="+endOffset+" / value="+value+")";
	}
}
