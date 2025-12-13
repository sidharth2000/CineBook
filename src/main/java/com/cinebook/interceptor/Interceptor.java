/**
 * @author Jun Lai
 * 
 * 
 */

package com.cinebook.interceptor;

public interface Interceptor {
	boolean before(Context context);

	void after(Context context);
}