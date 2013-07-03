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
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;

public class DefaultPolarityClassifier implements SentimentAnalysisTool{
	
	public static final int NGRAM = 8;

	List<String> categories;
    DynamicLMClassifier<NGramProcessLM> mClassifier;
    
    public DefaultPolarityClassifier(List<String> categories) {
		super();
		this.categories = categories;
		if(categories != null && categories.size() != 0){
			mClassifier      = DynamicLMClassifier.createNGramProcess(categories.toArray(new String[0]),NGRAM);
		}
	}
    
	private DefaultPolarityClassifier() {
		super();
		this.categories = new ArrayList<String>();
		mClassifier     = null;
	}
	
	private static DefaultPolarityClassifier instance;
	
	public static DefaultPolarityClassifier getInstance(){
		if(instance == null){
			instance = new DefaultPolarityClassifier();
		}
		return instance;
	}


	@Override
	public void init(List<String> categories) {
		if(categories != null && categories.size() > 0){
			this.categories = categories;
			mClassifier      = DynamicLMClassifier.createNGramProcess(this.categories.toArray(new String[0]),NGRAM);
		}		
	}

	@Override
	public boolean train(Map<String, List<String>> dataset) {
		if(dataset == null || dataset.isEmpty()) return false;
		init(new ArrayList<String>(dataset.keySet()));
		for(String cat :dataset.keySet()){
			if(cat == null || cat.isEmpty() || mClassifier == null) continue;
			Classification classification  = new Classification(cat);
			for(String text : dataset.get(cat)){
				if(text == null || text.isEmpty()) continue;
				Classified<CharSequence> classified = new Classified<CharSequence>(text,classification);
				mClassifier.handle(classified);
			}			
		}
		return false;
	}
	
	@Override
	public String evaluatetext(String text) {
		if(text == null || text.isEmpty() || mClassifier == null) return "";
		Classification classification = mClassifier.classify(text);
		return classification.bestCategory();
	}

	@Override
	public boolean isExportable() {
		return true;
	}

	@Override
	public File export() throws IOException {
		File src = new File(System.getProperty("java.io.tmpdir"),"DefaultPolarityClassifier.txt");
		if(!src.exists()) src.createNewFile();
		AbstractExternalizable.compileTo((Compilable) mClassifier, src);
		return src;
	}

	@Override
	public void init(File src) throws ClassNotFoundException, IOException {
		if (src != null) {
			mClassifier = (DynamicLMClassifier<NGramProcessLM>) AbstractExternalizable.readObject(src);
			categories = new ArrayList<String>();
			String[] tmp = mClassifier.categories();
			for (int i = 0; i < tmp.length; i++) {
				categories.add(tmp[i]);
			}
		}
	}

	
}
