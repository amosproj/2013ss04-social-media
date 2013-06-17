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
package com.amos.project4.socialMedia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.amos.project4.models.Client;
import com.amos.project4.utils.PaginatedList;

public class AccountSearchResult<T extends AccountSearchResultItem> implements PaginatedList<T> {

	@SuppressWarnings("unchecked")
	private final ArrayList<T> EMPTY_LIST = (ArrayList<T>) new ArrayList<AccountSearchResultItem>(0);
	private List<T> list;

	private static final int PAGE_SIZE = 20;
	private int index;

	private DataRetrieverInterface retriever;
	private AccountSearchResultInterface current_rslt;

	private Client client;

	/**
	 * @param PAGE_SIZE
	 */
	@SuppressWarnings("unchecked")
	public AccountSearchResult(DataRetrieverInterface retriever, Client client) {
		this.index = 0;
		this.retriever = retriever;
		this.client = client;
		this.current_rslt = this.retriever.makeSearch(this.client, 0, PAGE_SIZE);
		this.list = (List<T>) this.current_rslt.getValues();
		repaginate();
	}

	@SuppressWarnings("unchecked")
	private void repaginate() {
		if (this.current_rslt.getNumResults() == 0) {
			this.list = EMPTY_LIST;
		} else {
			int start = index * PAGE_SIZE;
			int end = start + PAGE_SIZE - 1;
			if (end >= this.current_rslt.getNumResults()) {
				end = (int) (this.current_rslt.getNumResults() - 1);
			}
			if (start >= this.current_rslt.getNumResults()) {
				index = 0;
				repaginate();
			} else if (start < 0) {
				index = (int) (this.current_rslt.getNumResults() / PAGE_SIZE);
				if (this.current_rslt.getNumResults() % PAGE_SIZE == 0) {
					index--;
				}
				repaginate();
			} else {
				this.current_rslt = this.retriever.makeSearch(client, start,PAGE_SIZE);
				this.list = (List<T>) this.current_rslt.getValues();
			}
		}
	}

	/* List accessors (uses page) */

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<T> iterator() {
		return list.iterator();
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		return (T[]) list.toArray();
	}
	
	public <T> T[] toArray(T[] a) {
		return (T[]) list.toArray(a);
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public T get(int index) {
		return list.get(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	/* List mutators (uses master list) */
	
	public boolean add(T o) {
		boolean b = list.add(o);
		repaginate();
		return b;
	}

	public boolean remove(Object o) {
		boolean b = list.remove(o);
		repaginate();
		return b;
	}

	public boolean addAll(Collection<? extends T> c) {
		boolean b = list.addAll(c);
		repaginate();
		return b;
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		boolean b = list.addAll(index, c);
		repaginate();
		return b;
	}

	public boolean removeAll(Collection<?> c){
		boolean b = list.removeAll(c);
		repaginate();
		return b;
	}

	public boolean retainAll(Collection<?> c) {
		boolean b = list.retainAll(c);
		repaginate();
		return b;
	}

	public void clear() {
		list.clear();
		repaginate();
	}

	public T set(int index, T element) {
		T o = list.set(index, element);
		repaginate();
		return o;
	}

	public void add(int index, T element) {
		list.add(index, element);
		repaginate();
	}

	public T remove(int index) {
		T o = list.remove(index);
		repaginate();
		return o;
	}

	/* Paginated List methods */

	public int getPageSize() {
		return PAGE_SIZE;
	}

	public boolean isFirstPage() {
		return index == 0;
	}

	public boolean isMiddlePage() {
		return !(isFirstPage() || isLastPage());
	}

	public boolean isLastPage() {
		return this.current_rslt.getNumResults() - ((index + 1) * PAGE_SIZE) < 1;
	}

	public boolean isNextPageAvailable() {
		return !isLastPage();
	}

	public boolean isPreviousPageAvailable() {
		return !isFirstPage();
	}

	public boolean nextPage() {
		if (isNextPageAvailable()) {
			index++;
			repaginate();
			return true;
		} else {
			return false;
		}
	}

	public boolean previousPage() {
		if (isPreviousPageAvailable()) {
			index--;
			repaginate();
			return true;
		} else {
			return false;
		}
	}

	public void gotoPage(int pageNumber) {
		index = pageNumber;
		repaginate();
	}

	public int getPageIndex() {
		return index;
	}
	
	public int getPageCount(){
		return (int) ((this.current_rslt.getNumResults()/ PAGE_SIZE) + 1);
	}
	
	public int getResultCount(){
		return (int) (this.current_rslt.getNumResults());
	}	
}