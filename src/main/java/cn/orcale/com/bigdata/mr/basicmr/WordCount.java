package cn.orcale.com.bigdata.mr.basicmr;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;


/***
 * 
 * <p>Title:</p>
 * <p>Desc :模仿wordcount</p>
 * @author yuhui
 * @date 2017年3月2日 下午9:16:24
 */
public class WordCount {
	
	static HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
	
	public static void main(String[] args) throws Exception {
		
		FileReader fileReader = new FileReader("data/NginxData.txt");
		
		BufferedReader br = new BufferedReader(fileReader);
		
		String line;
		while((line=br.readLine())!=null){
			
			String[] fields = StringUtils.split(line , "\t");
						
			String domain = fields[4];
			
			count(domain);
			
		}
		
		IOUtils.closeStream(br);
		
		File file = new File("data/wcount.txt");
		if(file.exists()) throw new RuntimeException("运行异常");
		FileWriter fw = new FileWriter(file,true);
		for(Entry<String, Integer> ent : wordsMap.entrySet()){
			
			fw.write(ent.getKey() + "\t" + ent.getValue());
			fw.write("\r\n");
			
		}
		
		IOUtils.closeStream(fw);
		
	}

	private static void count(String line) {
		String[] words = line.split(" ");
		Integer count;
		
		for(String word:words){
			
			count = wordsMap.get(word);
			if(count==null){
				wordsMap.put(word, 1);
			}else{
				count ++;
				wordsMap.put(word, count);
			}
		}
	}
}
