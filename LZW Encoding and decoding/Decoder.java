import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Siddhant Jain
 *
 */
public class Decoder {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = args[0]; // args[0] is the name of input file
		int decpos = filename.indexOf(".");
		String filewithoutext = filename.substring(0, decpos);
		String N = args[1]; //args[1] is the 
		int n = Integer.parseInt(N);
		final String FILENAME = "C:\\Users\\sid\\workspace\\AlgosProject1\\"+filename;
		String s = null;
		try {
			s = readFile(FILENAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] arr = new int[s.length()];
		for(int i=0; i<s.length();i++){
			arr[i] = s.charAt(i);
		}
		int Max_Table_Size = (int) Math.pow(2, n);//MAX_TABLE_SIZE=2(bit_length) bit_length is number of encoding bits
		HashMap<Integer, String> map = new HashMap();// initializing the dictionary 
		for(int j=0; j<=255; j++){
			char c = (char) j;//Initialise the table for first 256 characters and their code
			String s1 = Character.toString(c);
			map.put(j,s1);
		}
		int code = arr[0];
		String in = map.get(arr[0]);
		String newString = "";
		String temp= "";
		String temp2;
		ArrayList<String> sss = new ArrayList();
		String finalS = "";//main string, which will be given as output, is initialised to null
		finalS = in;	
		int k=1;
		//Below is the implementation of the pseudocode
		while(k<s.length()){
			code = arr[k];
			if(!map.containsKey(code)){
				temp = ""+in.charAt(0);
				newString = in+temp;
			}else{
				newString = map.get(code);
			}
			//System.out.println(newString);
			finalS = finalS+newString;
			temp2 = ""+newString.charAt(0);
			if(map.size()<Max_Table_Size){
				map.put(255+k, in+temp2);
			}
			in = newString;
			k++;
		}
		//below is the code for writing the output in the lzw file
		try{
		    PrintWriter writer = new PrintWriter(filewithoutext+"_decoded.txt", "UTF-8");
		    writer.print(finalS);
		    writer.close();
		} catch (IOException e) {
		   System.out.print("catch");
		}
	}
	//method to read input file and writing in string
	static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-16BE"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
