/*
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.amos.project4.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amos.project4.models.ClassifierDAO;
import com.amos.project4.models.ClassifierData;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.sentimentAnalysis.DefaultSentimentClassifier;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class ClassifierDataTest {
	
	private ClassifierDAO dao;
	private TwitterDataDAO t_dao;
	
	@Before
	public void setUp() throws Exception {
		dao = ClassifierDAO.getInstance();
		t_dao = TwitterDataDAO.getInstance();
	}
	
//	@Test	
//	public void checkAddData() throws IOException{
//		ClassifierData data = new ClassifierData();
//		data.setName("Twitter");
//		File f = new File("C:\\Users\\Silas\\Downloads","DefaultSentimentClassifier.model");
//		if(!f.exists()) f.createNewFile();
//		Path path = Paths.get(f.getAbsolutePath());
//		data.setClassifier(Files.readAllBytes(path));
//		
//		dao.addClassifierData(data);
//	}

//	@Test
//	public void checkAvailableClassifier() {
//		List<ClassifierData> datas = dao.getAllClassifierDatas();
//		assertTrue(!datas.isEmpty());
//	}
	
	@Test
	public void evaluationTest() throws IOException, ClassNotFoundException {
		List<ClassifierData> classifiers = dao.getAllClassifierDatas();
		File f = File.createTempFile("AMOSClassifier", ".model");
		FileOutputStream output = new FileOutputStream(f);
		output.write(classifiers.get(0).getClassifier());
		output.flush();
		output.close();
		DefaultSentimentClassifier classifier = DefaultSentimentClassifier.getInstance(f);
		
		List<TwitterData> tweets = t_dao.getAllTwitterDataOfClientByType(157, TwitterDataType.TWEETS);

		for(TwitterData t : tweets){
			String cat = classifier.evaluatetext(t.getDataString());
			System.out.println(cat  + " - " + t.getDataString());
		}
	}
	
	@After
	public void test() {
			
	}

}
