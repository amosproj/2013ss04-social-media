/*
 *
 * This file is part of the Datev and Social Media project.
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
package com.amos.project4.sentimentAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;

public class DefaultSentimentClassifier implements SentimentAnalysisTool {

	public static final int NGRAM = 8;

	private List<String> categories;
	private LMClassifier classifier;
	
	private static DefaultSentimentClassifier instance;
	
	public static DefaultSentimentClassifier getInstance() throws IOException{
		if(instance == null){
			instance = new DefaultSentimentClassifier();
		}
		return instance;
	}
	
	public static DefaultSentimentClassifier getInstance(File src) throws IOException, ClassNotFoundException{
		if(instance == null){
			instance = new DefaultSentimentClassifier(src);
		}
		return instance;
	}

	private DefaultSentimentClassifier() throws IOException {
		super();
	}

	private DefaultSentimentClassifier(File src) throws IOException, ClassNotFoundException {
		super();
		 init(src);
	}

	@Override
	public void init(List<String> categories) {
		if (categories != null && categories.size() > 0) {
			this.categories = categories;
			this.classifier = DynamicLMClassifier.createNGramProcess(
					this.categories.toArray(new String[0]), NGRAM);
		}
	}
	
	@Override
	public void init(File src) throws ClassNotFoundException, IOException {
		if (src != null) {
			classifier = (LMClassifier) AbstractExternalizable.readObject(src);
			categories = new ArrayList<String>();
			String[] tmp = classifier.categories();
			for (int i = 0; i < tmp.length; i++) {
				categories.add(tmp[i]);
			}
		}
	}

	@Override
	public boolean train(Map<String, List<String>> dataset) {
		if (dataset == null || dataset.isEmpty())
			return false;
		init(new ArrayList<String>(dataset.keySet()));
		for (String cat : dataset.keySet()) {
			if (cat == null || cat.isEmpty() || classifier == null)
				continue;
			Classification classification = new Classification(cat);
			for (String text : dataset.get(cat)) {
				if (text == null || text.isEmpty())
					continue;
				Classified<CharSequence> classified = new Classified<CharSequence>(text, classification);
				((ObjectHandler<Classified<CharSequence>>) classifier).handle(classified);
			}
		}
		return true;
	}

	@Override
	public String evaluatetext(String text) {
		if(text == null || text.isEmpty() || classifier == null) return "";
		Classification classification = classifier.classify(text);
		return classification.bestCategory();
	}

	@Override
	public boolean isExportable() {
		return true;
	}

	@Override
	public File export() throws IOException {
		File src = new File(System.getProperty("java.io.tmpdir"),"DefaultSentimentClassifier.txt");
		if(!src.exists()) src.createNewFile();
		AbstractExternalizable.compileTo((Compilable) classifier, src);
		return src;
	}

}
