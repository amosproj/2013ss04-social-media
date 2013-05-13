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
package com.amos.project4.controllers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.amos.project4.views.AbstractControlledView;
import com.amos.project4.views.AbstractViewModel;

public abstract class AbstractController implements Observer {

	protected ArrayList<AbstractControlledView> registeredViews;
	protected ArrayList<AbstractViewModel> registeredModels;

	public AbstractController() {
		registeredViews = new ArrayList<AbstractControlledView>();
		registeredModels = new ArrayList<AbstractViewModel>();
	}

	public void addModel(AbstractViewModel model) {
		registeredModels.add(model);
		model.addChangeListener(this);
	}

	public void removeModel(AbstractViewModel model) {
		registeredModels.remove(model);
		model.removeChangeListener(this);
	}

	public void addView(AbstractControlledView view) {
		registeredViews.add(view);
	}

	public void removeView(AbstractControlledView view) {
		registeredViews.remove(view);
	}

	// Use this to observe property changes from registered models
	// and propagate them on to all the views.
	@Override
	public void update(Observable o, Object arg) {
		updateInternally(arg, o);
		for (AbstractControlledView view : registeredViews) {
			if(view != null)
				view.modelPropertyChange(o, arg);
		}		
	}
	
	public abstract void updateInternally(Object arg, Observable o);

	/**
	 * This is a convenience method that subclasses can call upon to fire
	 * property changes back to the models. This method uses reflection to
	 * inspect each of the model classes to determine whether it is the owner of
	 * the property in question. If it isn't, a NoSuchMethodException is thrown,
	 * which the method ignores.
	 * 
	 * @param propertyName
	 *            = The name of the property.
	 * @param newValue
	 *            = An object that represents the new value of the property.
	 */
	protected void setModelProperty(String propertyName, Object newValue) {
		for (AbstractViewModel model : registeredModels) {
			try {

				Method method = model.getClass().getMethod(
						"set" + propertyName,
						new Class[] { newValue.getClass() }

				);
				method.invoke(model, newValue);

			} catch (Exception ex) {
				// Handle exception.
			}
		}
	}
}