package com.synacktiv;

import java.io.*;
import java.util.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

// jdeserialize
import org.unsynchronized.*;

// jcommander
import com.beust.jcommander.JCommander;

public class inyourface extends jdeserialize {

	public static final String INDENT = "    ";
	private String outFile = null;
	private LinkedList<Patch> patches = new LinkedList<Patch>();
	private boolean isUrlEncoded = false;
	private boolean isB64Encoded = false;
	private boolean isGZipped = false;
	private boolean rawOutput = false;

	public inyourface(String filename) {
		super(filename);
	}

	public ByteArrayInputStream decode(InputStream viewstate) {
		String _tmp = "";
		ByteArrayInputStream _ret = null;

		try {
			// 1. read the stream
			int nb_read = 0;
			byte[] buf = new byte[8196];
			while((nb_read = viewstate.read(buf, 0, buf.length)) >= 0) {
				_tmp += new String(buf, 0, nb_read, "ISO-8859-1");
			}

			// 2. url decode if encoded
			if(_tmp.contains("%")) {
				_tmp = URLDecoder.decode(_tmp, "ISO-8859-1");
				isUrlEncoded = true;
			}

			// 3. decode b64
			try {
				_tmp = Base64.decode(_tmp);
				isB64Encoded = true;
			} catch (Exception b64e) {
				// do nothing: it probably means that the input stream is not b64 encoded.
			}	

			// 4. unzip
			String out_str = "";
			try {
				GZIPInputStream gzis = new GZIPInputStream(new ByteArrayInputStream(_tmp.getBytes("ISO-8859-1")));
				buf = new byte[8196];
				nb_read = 0;
				while ((nb_read = gzis.read(buf, 0, buf.length)) >= 0) {
					out_str += new String(buf, 0, nb_read, "ISO-8859-1");
				}
				gzis.close();
				isGZipped = true;
			} catch(IOException ioe) {
				// do nothing: it probably means that the input stream is not gzipped
				out_str = _tmp;
			}

			// 5. create a ByteArrayInputStream
			_ret = new ByteArrayInputStream(out_str.getBytes("ISO-8859-1"));
		} catch(Exception e) {
			e.printStackTrace();
		}

		return _ret;
	}

	public String encode(ByteArrayOutputStream to_encode) {
		String _ret = null;

		try {
			// 1. zip
			byte[] _tmp_bytes = to_encode.toByteArray();
			if(isGZipped) {
				ByteArrayOutputStream zipped = new ByteArrayOutputStream();
				GZIPOutputStream gzip = new GZIPOutputStream(zipped);
				gzip.write(_tmp_bytes);
				gzip.close();
				_tmp_bytes = zipped.toByteArray();
			}

			// 2. encode b64
			String _tmp = new String(_tmp_bytes, "ISO-8859-1"); 
			if(isB64Encoded) {
				_tmp = Base64.encode(_tmp);
			}

			// 3. url encode
			if(isUrlEncoded) {
				_tmp = URLEncoder.encode(_tmp, "ISO-8859-1");
			}

			_ret = _tmp;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return _ret;
	}

	private content getContentByHandle(int handle)
	{
		for (Map<Integer,content> map : getHandleMaps()) {
			return map.get(handle);
		}
		return null;
	}

	private Object getInstanceField(instance i, String field_name) throws IOException
	{
		Object native_val = null;
		for (classdesc cd : i.fielddata.keySet()) {
			for (field f: i.fielddata.get(cd).keySet()) {
				if (f.name.equals(field_name)) {
					if (native_val != null) {
						throw new IOException("dup native val");
					}
					native_val = i.fielddata.get(cd).get(f);
					break;
				}
			}
		}

		if (native_val == null) {
			throw new IOException("native val empty ??? handle=" + hex(i.handle));
		}

		return native_val;
	}
	private Object getJavaLangValue(instance i) throws IOException
	{
		return getInstanceField(i, "value");
	}

	public void hideFieldRefs(instance i, ArrayList<Integer> hidden_handles)
		throws IOException
	{
		if (i.classdesc.name.startsWith("java.lang."))
			return;

		if ((i.annotations != null)
				&& (i.classdesc.name.equals("java.util.ArrayList") || (i.classdesc.name.equals("java.util.HashMap")))) {

			for (classdesc cd: i.annotations.keySet()) {
				for (content c: i.annotations.get(cd)) {
					if (c instanceof instance) {
						hidden_handles.add(((instance)c).handle);
					}
				}
			}
				}

		for (classdesc cd : i.fielddata.keySet()) {
			for (field f: i.fielddata.get(cd).keySet()) {
				Object obj = i.fielddata.get(cd).get(f);

				if (obj instanceof instance) {

					instance i2 = (instance) obj;
					hidden_handles.add(i2.handle);

				} else if (obj instanceof arrayobj) {

					for (Object obj2 : ((arrayobj)obj).data) {
						if (obj2 instanceof contentbase)
							hidden_handles.add(((contentbase)obj2).handle);
					}

				} else if (obj instanceof stringobj) {
					hidden_handles.add(((stringobj)obj).handle);
				}
			}
		}

	}

	public String prettyString(stringobj str)
	{
		return new StringBuffer().append('"').append(str.value)
			.append("\" (").append(hex(str.handle)).append(')')
			.toString();
	}

	public void prettyDumpContent(StringBuffer sb, String indent, classdesc cd, content c)
		throws IOException
	{
		if (c instanceof blockdata)
			return;

		if (c instanceof instance) {
			instance i = (instance) c;
			if (i.classdesc.name.startsWith("java.lang.")) {
				Object native_val = getJavaLangValue(i);
				sb.append(indent).append(native_val.toString()).append('\n');
			} else {
				prettyDumpInstance(sb, indent, i);
			}
		} else if (c instanceof arrayobj) {
			prettyDumpArray(sb, indent, (arrayobj)c);


		} else if (c instanceof stringobj) {
			sb.append(indent).append(prettyString((stringobj)c)).append('\n');

		} else {
			sb.append(indent).append(cd == null ? "" : cd.name).append(' ').append(c == null ? "" : c.toString()).append('\n');

		}
	}

	public void prettyDumpHashMap(StringBuffer sb, String indent, instance i)
		throws IOException
	{
		if (i.annotations == null) {
			throw new IOException("array missing items");
		}

		int c = 0;
		for (classdesc cd: i.annotations.keySet()) {
			String key = null, val = null;
			for (content c2: i.annotations.get(cd)) {
				if (c2 instanceof blockdata)
					continue;

				if ((c & 1) == 1) {
					// parse value
					//				 val = c2.toString();
					//sb.append(indent).append(key).append(" => ").append(val).append('\n');
					sb.append(indent).append(key).append(" => ");
					prettyDumpContent(sb, indent, null, c2);
					key = null;
					val = null;
				} else {
					// parse key
					if (!(c2 instanceof stringobj))
						throw new IOException("wtf hashmap key not string handle="+hex(i.handle));
					key = prettyString((stringobj)c2);
				}
				++c;
			}
		}
	}
	public void prettyDumpArrayList(StringBuffer sb, String indent, instance i)
		throws IOException
	{
		int size = ((Number) getInstanceField(i, "size")).intValue();

		if (i.annotations == null) {
			throw new IOException("array missing items");
		}

		for (classdesc cd: i.annotations.keySet()) {
			for (content c2: i.annotations.get(cd)) {
				prettyDumpContent(sb, indent, cd, c2);
			}
		}
	}

	public void prettyDumpArray(StringBuffer sb, String indent, arrayobj ar)
		throws IOException
	{
		for (Object obj : ar.data) {
			if (obj == null) {
				sb.append(indent).append("null\n");
			} else if (obj instanceof content) {
				prettyDumpContent(sb, indent, null, (content)obj);
				//				 prettyDumpContent(sb, indent, null, );
			} else {
				sb.append(indent).append(obj).append('\n');
			}
		}
	}

	public void prettyDumpInstance(StringBuffer sb, String indent, instance i)
		throws IOException
	{
		sb.append(indent).append(hex(i.handle)).append(" ").append(i.classdesc.name);

		if (i.classdesc.name.startsWith("java.lang.")) {
			Object native_val = getJavaLangValue(i);
			sb.append(" = ").append(native_val.toString());

		} else if (i.classdesc.name.equals("java.util.ArrayList")) {

			sb.append(" = [\n");
			prettyDumpArrayList(sb, indent+INDENT, i);
			sb.append(indent).append("]\n");

		} else if (i.classdesc.name.equals("java.util.HashMap")) {

			sb.append(" = {\n");
			prettyDumpHashMap(sb, indent+INDENT, i);
			sb.append(indent).append("}\n");

		} else {
			sb.append(" = {\n");
			// TODO sort fields by name
			String indent2 = indent + INDENT;
			for (classdesc cd : i.fielddata.keySet()) {
				for (field f: i.fielddata.get(cd).keySet()) {
					Object obj = i.fielddata.get(cd).get(f);
					String jtype = f.getJavaType();
					if (jtype.startsWith("java.lang."))
						jtype = jtype.substring(10);
					sb.append(indent2).append(jtype).append(' ').append(f.name).append(" = ");

					if (obj instanceof instance) {
						instance i2 = (instance) obj;

						if (i2.classdesc.name.startsWith("java.lang.")) {
							sb.append(getJavaLangValue(i2).toString()).append(" (").append(hex(i2.handle)).append(')');

						} else if (i2.classdesc.name.equals("java.util.ArrayList")) {
							sb.append('(').append(hex(i2.handle)).append(") [\n");
							prettyDumpArrayList(sb, indent2+INDENT, i2);
							sb.append(indent2).append(']');

						} else {
							sb.append(" {\n");
							prettyDumpInstance(sb, indent2+INDENT, i2);
							sb.append(indent2).append('}');
						}

					} else if (obj instanceof stringobj) {
						stringobj str = (stringobj)obj;
						sb.append('"').append(str.value).append("\" (").append(hex(str.handle)).append(')');

					} else if (obj instanceof classobj) {
						classobj klass = (classobj) obj;
						sb.append(klass.classdesc.name).append(" (").append(hex(klass.classdesc.handle)).append(')');

					} else if (obj instanceof arrayobj) {
						arrayobj ar = (arrayobj) obj;
						sb.append('(').append(hex(ar.handle)).append(") [\n");
						prettyDumpArray(sb, indent2+INDENT, ar);
						sb.append(indent2).append(']');

					} else {
						sb.append(obj);
					}
					sb.append('\n');
				}
			}
			sb.append(indent).append("}\n");
		}

	}

	public void prettyDump() throws IOException
	{
		// TODO: sort by class

		ArrayList<Integer> hidden_handles = new ArrayList<Integer>();

		for (Map<Integer,content> map : getHandleMaps()) {

			for (content c: map.values()) {

				if (c instanceof arrayobj) {

					for (Object obj2 : ((arrayobj)c).data) {
						if (obj2 instanceof instance)
							hideFieldRefs((instance) obj2, hidden_handles);
					}
					continue;
				}

				if (!(c instanceof instance))
					continue;
				hideFieldRefs((instance) c, hidden_handles);
			}
		}

		for (content c: getContent()) {
			StringBuffer sb = new StringBuffer();
			prettyDumpContent(sb, "", null, c);
			System.out.println(sb.append('\n').toString());
		}
	}

	//  patch methods
	public void rawpatch(String infile, String[] args) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer();

		FileInputStream file = new FileInputStream(args[2]);
		byte[] buf = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nb_read = 0;
		while((nb_read = file.read(buf, 0, buf.length)) != -1) {
			baos.write(buf, 0, nb_read);
		}
		baos.flush();

		if(args != null && args.length == 3) { 
			patches.add(new Patch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), baos));
		}	
	}

	public void patch(String infile, String[] args) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer();
		if(args != null && args.length == 3) {
			// parse instance handle
			String arg = args[0];
			if (arg.startsWith("0x")) {
				arg = arg.substring(2);
			}
			int handle = Integer.parseInt(arg, 16);
			content c = getContentByHandle(handle);
			System.out.println("===> " + c + " @ from " + c.getStartOffset() + " to " + c.getEndOffset());

			// parse field name
			arg = args[1];

			if(c instanceof instance) {
				System.out.println("patching instance");

				// parsing arguments
				String field2patch = args[1];
				String value2patch = args[2];

				// parsing object
				instance i = (instance) c;
				field target_field = null;
				if(i.fielddata != null && i.fielddata.size() > 0) {
					// for each classdesc
					for(classdesc cd: i.fielddata.keySet()) {
						// for each field
						for(field f: i.fielddata.get(cd).keySet()) {
							// if current field = field to patch
							if (f.name.equals(field2patch)) {
								// get the field content
								Integer startOffset = i.fieldStartOffsets.get(cd).get(f);
								Integer endOffset = i.fieldEndOffsets.get(cd).get(f);
								Object o = i.fielddata.get(cd).get(f);
								System.out.println("field to patch @ from " + startOffset + " to " + endOffset + " | " + o);

								// register the patch
								patches.add(new Patch(startOffset, endOffset, Convertor.convert(value2patch, o.getClass().getName())));
								System.out.println("new patch object registered / s_offset=" + startOffset + " / e_offset=" + endOffset + " / value=" + value2patch);
							}
						}
					}
					System.out.println(sb.toString());
				}
			} else if(c instanceof stringobj) {
				String value2patch = args[2];
				Integer startOffset = c.getStartOffset()-1;
				Integer endOffset = c.getEndOffset();
				patches.add(new Patch(startOffset, endOffset, Convertor.convert(value2patch, org.unsynchronized.stringobj.class.getName())));					
				System.out.println("new string patch registered / s_offset=" + startOffset + " / e_offset=" + endOffset + " / value=" + value2patch);
			} else {
				System.out.println("Unknown class: " + c.getClass());
			}
		}
	}

	/// commit patch method
	public void commitPatches(String infile) throws Exception {
		// sort patches
		Collections.sort(patches, new Comparator<Patch>() {
			public int compare(Patch p1, Patch p2) {
				return p1.getStartOffset() - p2.getStartOffset();
			}
		});

		// open the input file
		FileInputStream fis = new FileInputStream(infile);
		ByteArrayInputStream bais = decode(fis);

		// create the output buffer
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Iterator<Patch> iter = patches.iterator();
		Patch currentPatch = null;
		int nextStartOffset = 0;
		int nextEndOffset = 0;
		int currentOffset = 0;
		int objSize = 0;
		int nb_read = 0;
		while(iter.hasNext()) {
			// retrieve the current patch
			currentPatch = iter.next();

			// retrieve the patch offsets
			nextStartOffset = currentPatch.getStartOffset();
			nextEndOffset = currentPatch.getEndOffset();
			objSize = nextEndOffset - nextStartOffset;

			// retrieve the patch value
			Object obj = currentPatch.getValue();

			String objstr;
			if(obj instanceof java.io.ByteArrayOutputStream) {
				objstr = new String(((ByteArrayOutputStream) obj).toByteArray());
			} else {
				objstr = obj.toString();
			}

			System.out.println("patching object @ s_offset=" + nextStartOffset + " / e_offset=" + nextEndOffset + " / size=" + objSize + " / value=" + objstr);

			// init the buffer that will contain data from the current offset
			// to the next patch offset.
			byte[] buf = new byte[nextStartOffset-currentOffset];

			// write data to the nextoffset
			nb_read = bais.read(buf, 0, buf.length);

			if(nb_read != (nextStartOffset-currentOffset)) {
				System.out.println("Looking for offset @ " + nextStartOffset + " that does not exist.");
				break;
			}

			// write stream to the current offset
			baos.write(buf, 0, nb_read);

			// write our patch
			byte[] rawobj;
			if(obj instanceof java.io.ByteArrayOutputStream) {
				rawobj = ((ByteArrayOutputStream) obj).toByteArray();
			} else {
				rawobj = serializeObject(obj);
			}
			baos.write(rawobj, 0, rawobj.length);

			// drop patched data from the input stream
			// write byte in other file
			bais.skip(objSize);
			currentOffset += objSize + nb_read;
		}

		// write the last bytes
		byte[] buf = new byte[8196];
		while((nb_read = bais.read(buf, 0, buf.length)) > 0) {	
			baos.write(buf, 0, nb_read);
		}

		OutputStream fos = null;
		if(outFile != null) {
			fos = new FileOutputStream(outFile);
		} else {
			fos = System.out;
		}    

		if(rawOutput) {
			fos.write(baos.toByteArray());
		} else {
			fos.write(encode(baos).getBytes("ISO-8859-1"));
		}    

		if(outFile != null) {
			fos.close();
		} else {
			System.out.println("");
		}  
	}

	// serialize object
	public byte[] serializeObject(Object obj) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream objos = new ObjectOutputStream(baos);

		// if the patched object is not a primitive type, we skip 4 bytes
		// else we skip 6 bytes in the output stream
		boolean primitive = false;

		// switch case on the obj type
		if(obj instanceof java.lang.Integer) {
			objos.writeInt(((Integer)obj).intValue());
			primitive = true;
		} else if(obj instanceof java.lang.Long) {
			objos.writeLong(((Long)obj).longValue());
			primitive = true;
		} else if(obj instanceof java.lang.Boolean) {
			objos.writeBoolean(((Boolean)obj).booleanValue());
			primitive = true;
		} else if(obj instanceof java.lang.Byte) {
			objos.writeByte(((Byte)obj).byteValue());
			primitive = true;
		} else if(obj instanceof java.lang.Character) {
			objos.writeChar(((Character)obj).charValue());
			primitive = true;
		} else if(obj instanceof java.lang.Double) {
			objos.writeDouble(((Double)obj).doubleValue());
			primitive = true;
		} else if(obj instanceof java.lang.Float) {
			objos.writeFloat(((Float)obj).floatValue());
			primitive = true;
		} else if(obj instanceof java.lang.Short) {
			objos.writeShort(((Short)obj).shortValue());
			primitive = true;
		} else if(obj instanceof java.lang.Object) {
			objos.writeObject(obj);
		} else {
			throw new Exception(obj.getClass().getName() + " patching is not implemented yet.");
		}

		// flushing stream
		objos.flush();

		// remove the 4 or 6 first bytes (serialization headers)
		int toskip = (primitive) ? 6 : 4;
		byte[] tmpobj = baos.toByteArray();
		byte[] rawobj = new byte[tmpobj.length-toskip];
		System.arraycopy(tmpobj, toskip, rawobj, 0, rawobj.length);

		// return raw object	
		return rawobj;
	}

	public static void main(String[] args) {

		ByteArrayInputStream bais = null;
		inyourface iyf = null;
		boolean hasPatches = false;

		// parse arguments
		CommandLine cmdline = new CommandLine();
		JCommander jcmd = new JCommander(cmdline, args);

		if(cmdline.help || args.length == 0) {
			jcmd.setProgramName("inyourface");
			jcmd.usage();
			System.exit(1);
		}

		String jsf_filename = cmdline.args.get(0);
		try {

			iyf = new inyourface(jsf_filename);
			bais = iyf.decode(new FileInputStream(jsf_filename));
			
			// arguments
			iyf.rawOutput = cmdline.raw;
			iyf.outFile = cmdline.outfile;
			
			// run
			iyf.run(bais, true);

			if(cmdline.pretty) {
				iyf.prettyDump();
			} else if(cmdline.getPatchCount() > 0 || cmdline.getRawPatchCount() > 0) {
				for(int i = 0; i<cmdline.getPatchCount(); i++) {
					iyf.patch(jsf_filename, cmdline.getPatch(i));
				}
				
				for(int i = 0; i<cmdline.getRawPatchCount(); i++) {
					iyf.rawpatch(jsf_filename, cmdline.getRawPatch(i));
				}
				
				iyf.commitPatches(jsf_filename);
			} else {
				// parse and dump with jdeserialize style
				// -> should be remove in the future
				Getopt go = new Getopt();
	        		try {
	        		    go.parse(args);
	        		} catch (Getopt.OptionParseException ope) {
	        		}
				iyf.dump(go);
				/// end
			}
		} catch(Exception e) {
			debugerr("wtf error: " + e.getMessage());
			e.printStackTrace();

		} finally {
			if(bais != null) {
				try {
					bais.close();
				} catch (Exception ignore) { }
			}
		}
	}
}
