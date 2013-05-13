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
package com.amos.project4.views;

import java.util.Observable;
import java.util.Observer;

public abstract class AbstractViewModel extends Observable {

	public AbstractViewModel() {
	}

	public void addChangeListener(Observer listener) {
		this.addObserver(listener);
	}

	public void removeChangeListener(Observer listener) {
		this.deleteObserver(listener);
	}

	protected void fireChange(String propertyName, Object oldValue,
			Object newValue) {
		this.setChanged();
		this.notifyObservers(newValue);
	}
}