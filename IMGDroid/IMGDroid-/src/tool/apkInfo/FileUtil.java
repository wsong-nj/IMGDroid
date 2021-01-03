package tool.apkInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FileUtil {
	
	public static void saveResult(String filePath, Collection<String> set, boolean append) {
		try {
			FileWriter writer = new FileWriter(filePath, append);
			for(String str : set) {
				writer.write(str + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveResult(String path, Map<String, String> map, boolean append) {
		String filePath = path;
		List<String> list = new ArrayList<>();
		for(String key : map.keySet()) {
			list.add(key + " : " + map.get(key));
		}
		Collections.sort(list);
		saveResult(filePath, list, append);
	}
	
	public static void saveResult(String path, Collection<String> set, String fileName, boolean append) {
		String filePath = path + File.separator + fileName;
		saveResult(filePath, set, append);
	}

	/**
	 * readLine from file, save result to list
	 * @param filePath
	 * @return : result list
	 */
	public static List<String> readFile(String filePath) {
		List<String> content = new ArrayList<>();
		if(!(new File(filePath).exists()))
			return content;
		FileInputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			inputStream = new FileInputStream(filePath);
			isr = new InputStreamReader(inputStream);
			br = new BufferedReader(isr);
			while(true) {
				String line = br.readLine();
				if(line == null)
					break;
				
				content.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(br != null)
					br.close();
				if(isr != null)
					isr.close();
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return content;
	}
}
