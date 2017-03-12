import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import sun.print.resources.serviceui;

/**
 * 
 */

/**
 * @author Siddhant Jain
 *
 */
public class Encoder {

	/**
	 * @param args
	 */
	
	public static void main(String[] args){
		String input_file_name = args[0];//args[0] is the name of input file
		int decpos = input_file_name.indexOf(".");
		String filewithoutext = input_file_name.substring(0, decpos);
		String encoding_bits_string = args[1];//args[1] is the number of encoding bits
		int encoding_bits = Integer.parseInt(encoding_bits_string);
		final String input_file_path = "C:\\test\\"+input_file_name;
		String input_text_string = null; // all text which should be stored in the string
		try {
			input_text_string = readFile(input_file_path);//reading input and writing in string
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int Max_Table_Size = (int) Math.pow(2, encoding_bits);//MAX_TABLE_SIZE=2(bit_length) bit_length is number of encoding bits
		HashMap<String, Integer> map = new HashMap(); //The map is the table where code is to be stored
		for(int j=0; j<=255; j++){
			char c = (char) j; //Initialise the table for first 256 characters and their code
			String s1 = Character.toString(c);
			map.put(s1, j);
		}
		String in = ""; //main string, whose code will be given as output, is initialised to null
		int k=0;
		int counter=0;
		String symbol = ""; //string where input symbols are taken
		String look = in+symbol;
		List<Integer> lines = new ArrayList();//this the the list of output
		char caaa = 'h';
		//Below is the implementation of the pseudocode
		while(k<input_text_string.length()-1){
			char sym = input_text_string.charAt(k);
			symbol = Character.toString(sym);
			look = in+symbol;
			if(map.containsKey(look)){
				in = in+symbol;
			}else{
				int code1;
				int code;
				code1 = 256+counter;
				counter++;
				if(map.containsKey(in)){
					code = map.get(in);
					lines.add(code);
					caaa = (char) code;
				}else{
					lines.add(code1);
				}
				if(map.size()<Max_Table_Size){
					map.put(look, code1);
				}
				in = symbol;
			}
			k++;
		} 
		lines.add(map.get(in));
		//below is the code for writing the output in the lzw file
		try{
		    PrintWriter writer = new PrintWriter(filewithoutext+".lzw", "UTF-16BE");
		    for(int l=0;l<lines.size();l++){
		    	int temp = lines.get(l);
		    	char tempc = (char) temp;
		    	writer.print(tempc);
		    }
		    writer.close();
		} catch (IOException e) {
		   System.out.print("catch");
		}
	}
	//method to read input file and writing in string
	static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

}
