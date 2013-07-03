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
import java.util.List;
import java.util.Map;

public interface SentimentAnalysisTool {	
	
	public void init(List<String> categories);
	public boolean train(Map<String,List<String>> dataset);
	public String evaluatetext(String text);
	public boolean isExportable();
	public File export() throws IOException;
	void init(File src) throws ClassNotFoundException, IOException;
}
