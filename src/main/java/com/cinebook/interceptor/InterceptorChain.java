/**
 * @author Jun Lai
 * 
 * 
 */

package com.cinebook.interceptor;

import java.util.ArrayList;
import java.util.List;

public class InterceptorChain {
    private final List<Interceptor> interceptors = new ArrayList<>();

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public boolean before(Context context) {
        for (Interceptor interceptor : interceptors) {
            if (!interceptor.before(context)) {
                return false;
            }
        }
        return true;
    }

    public void after(Context context) {
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).after(context);
        }
    }
}